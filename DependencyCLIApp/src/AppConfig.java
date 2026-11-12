

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    private static final int FIRST_NODE_INDEX = 0;

    private String packageName;
    private String repositoryUrl;
    private String testRepositoryPath;
    private boolean testMode;
    private String graphImageFile;
    private boolean asciiTree;
    private String packageFilter;

    public static AppConfig fromXml(Path path) throws ConfigurationException {
        List<String> errors = new ArrayList<>();
        Element root = readRoot(path);
        AppConfig config = new AppConfig();

        readTextValues(root, config);
        readBooleanValues(root, config, errors);
        validateConfig(config, errors);

        if (!errors.isEmpty()) {
            throw new ConfigurationException(errors);
        }
        return config;
    }

    private static Element readRoot(Path path) throws ConfigurationException {
        File xmlFile = path.toFile();
        if (!xmlFile.exists()) {
            throw new ConfigurationException(List.of("Файл конфигурации не найден: " + path));
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(xmlFile);
            Element root = document.getDocumentElement();

            if (!root.getTagName().equals("config")) {
                throw new ConfigurationException(List.of("Главный тег должен быть <config>"));
            }
            return root;
        } catch (ConfigurationException e) {
            throw e;
        } catch (Exception e) {
            throw new ConfigurationException(List.of("Ошибка чтения XML: " + e.getMessage()));
        }
    }

    private static void readTextValues(Element root, AppConfig config) {
        config.packageName = getValue(root, "packageName");
        config.repositoryUrl = getValue(root, "repositoryUrl");
        config.testRepositoryPath = getValue(root, "testRepositoryPath");
        config.graphImageFile = getValue(root, "graphImageFile");
        config.packageFilter = getValue(root, "packageFilter");
    }

    private static void readBooleanValues(Element root, AppConfig config, List<String> errors) {
        String testModeText = getValue(root, "testMode");
        String asciiTreeText = getValue(root, "asciiTree");

        config.testMode = readBoolean(testModeText, "testMode", errors);
        config.asciiTree = readBoolean(asciiTreeText, "asciiTree", errors);
    }

    private static boolean readBoolean(String value, String name, List<String> errors) {
        if (value.equals("true") || value.equals("false")) {
            return Boolean.parseBoolean(value);
        }
        errors.add(name + " должен быть true или false");
        return false;
    }

    private static void validateConfig(AppConfig config, List<String> errors) {
        if (config.packageName.isEmpty()) {
            errors.add("Не указано имя пакета");
        }
        if (config.graphImageFile.isEmpty() || !config.graphImageFile.endsWith(".png")) {
            errors.add("Имя изображения должно оканчиваться на .png");
        }
        if (config.testMode) {
            validateTestFile(config.testRepositoryPath, errors);
        } else {
            validateUrl(config.repositoryUrl, errors);
        }
    }

    private static void validateTestFile(String fileName, List<String> errors) {
        if (fileName.isEmpty()) {
            errors.add("В тестовом режиме нужно указать testRepositoryPath");
        } else if (!new File(fileName).isFile()) {
            errors.add("Тестовый файл не найден: " + fileName);
        }
    }

    private static void validateUrl(String url, List<String> errors) {
        if (url.isEmpty()) {
            errors.add("Не указан URL репозитория");
        } else if (!url.startsWith("http://") && !url.startsWith("https://")) {
            errors.add("URL должен начинаться с http:// или https://");
        }
    }

    private static String getValue(Element root, String tagName) {
        NodeList nodes = root.getElementsByTagName(tagName);
        if (nodes.getLength() == FIRST_NODE_INDEX) {
            return "";
        }
        return nodes.item(FIRST_NODE_INDEX).getTextContent().trim();
    }

    public String getPackageName() { return packageName; }
    public String getRepositoryUrl() { return repositoryUrl; }
    public String getTestRepositoryPath() { return testRepositoryPath; }
    public boolean isTestMode() { return testMode; }
    public String getPackageFilter() { return packageFilter; }

    public void print() {
        System.out.println("packageName = " + packageName);
        System.out.println("repositoryUrl = " + repositoryUrl);
        System.out.println("testRepositoryPath = " + testRepositoryPath);
        System.out.println("testMode = " + testMode);
        System.out.println("graphImageFile = " + graphImageFile);
        System.out.println("asciiTree = " + asciiTree);
        System.out.println("packageFilter = " + packageFilter);
    }
}
