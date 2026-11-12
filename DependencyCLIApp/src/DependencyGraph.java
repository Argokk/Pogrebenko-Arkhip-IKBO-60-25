import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DependencyGraph {
    private final AppConfig config;
    private final PyPiClient client = new PyPiClient();
    private final TestRepository testRepository;

    public DependencyGraph(AppConfig config) throws IOException {
        this.config = config;
        testRepository = config.isTestMode()
                ? new TestRepository(config.getTestRepositoryPath()) : null;
    }

    public Map<String, List<String>> build() throws IOException, InterruptedException {
        Map<String, List<String>> graph = new LinkedHashMap<>();
        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        String root = config.getPackageName();
        if (filtered(root)) {
            throw new IOException("Исходный пакет исключён фильтром: " + root);
        }
        queue.add(root);
        visited.add(key(root));

        while (!queue.isEmpty()) {
            String current = queue.remove();
            List<String> dependencies = getDependencies(current);
            List<String> included = new ArrayList<>();
            for (String dependency : dependencies) {
                String name = config.isTestMode() ? dependency : packageName(dependency);
                if (!filtered(name)) {
                    included.add(name);
                    if (visited.add(key(name))) {
                        queue.add(name);
                    }
                }
            }
            graph.put(current, included);
        }
        return graph;
    }

    private List<String> getDependencies(String name) throws IOException, InterruptedException {
        if (config.isTestMode()) {
            return testRepository.getDependencies(name);
        }
        return client.getDirectDependencies(config.getRepositoryUrl(), name);
    }

    private boolean filtered(String name) {
        String filter = config.getPackageFilter();
        return !filter.isEmpty() && name.contains(filter);
    }

    private String packageName(String requirement) throws IOException {
        Matcher matcher = Pattern.compile("^[A-Za-z0-9][A-Za-z0-9._-]*").matcher(requirement.trim());
        if (!matcher.find()) {
            throw new IOException("Не удалось прочитать имя зависимости: " + requirement);
        }
        return matcher.group();
    }

    private String key(String name) {
        if (config.isTestMode()) {
            return name;
        }
        return name.toLowerCase(java.util.Locale.ROOT).replaceAll("[-_.]+", "-");
    }
}
