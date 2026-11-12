import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestRepository {
    private final Map<String, List<String>> packages = new LinkedHashMap<>();

    public TestRepository(String fileName) throws IOException {
        for (String line : Files.readAllLines(Path.of(fileName))) {
            readLine(line.trim());
        }
    }

    private void readLine(String line) throws IOException {
        if (line.isEmpty()) {
            return;
        }
        String[] parts = line.split(":", -1);
        if (parts.length != 2 || !parts[0].trim().matches("[A-Z]+")) {
            throw new IOException("Неверная строка тестового репозитория: " + line);
        }
        String name = parts[0].trim();
        List<String> dependencies = new ArrayList<>();
        String values = parts[1].trim();
        if (!values.isEmpty()) {
            for (String dependency : values.split("\\s+")) {
                if (!dependency.matches("[A-Z]+")) {
                    throw new IOException("Неверное имя пакета: " + dependency);
                }
                dependencies.add(dependency);
            }
        }
        if (packages.putIfAbsent(name, dependencies) != null) {
            throw new IOException("Повторное описание пакета: " + name);
        }
    }

    public List<String> getDependencies(String name) throws IOException {
        if (!packages.containsKey(name)) {
            throw new IOException("Пакет отсутствует в тестовом репозитории: " + name);
        }
        return packages.get(name);
    }
}
