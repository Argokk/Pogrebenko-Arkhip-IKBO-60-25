import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    private static final int EXPECTED_ARGUMENTS = 1;
    private static final int WRONG_ARGUMENTS_CODE = 1;
    private static final int CONFIGURATION_ERROR_CODE = 2;
    private static final int REPOSITORY_ERROR_CODE = 3;
    private static final int INTERRUPTED_ERROR_CODE = 4;

    public static void main(String[] args) {
        if (args.length != EXPECTED_ARGUMENTS) {
            System.err.println("Usage: java Main <path-to-config.xml>");
            System.exit(WRONG_ARGUMENTS_CODE);
        }

        try {
            AppConfig config = AppConfig.fromXml(Path.of(args[0]));
            DependencyGraph builder = new DependencyGraph(config);
            Map<String, List<String>> graph = builder.build();
            for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        } catch (ConfigurationException e) {
            printErrors(e);
            System.exit(CONFIGURATION_ERROR_CODE);
        } catch (IOException e) {
            System.err.println("Repository error: " + e.getMessage());
            System.exit(REPOSITORY_ERROR_CODE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Request was interrupted");
            System.exit(INTERRUPTED_ERROR_CODE);
        }
    }

    private static void printErrors(ConfigurationException exception) {
        System.err.println("Configuration errors:");

        for (String error : exception.errors()) {
            System.err.println("- " + error);
        }
    }

    private static void printDependencies(String packageName, List<String> dependencies) {
        System.out.println("Direct dependencies of " + packageName + ":");

        if (dependencies.isEmpty()) {
            System.out.println("No direct dependencies");
            return;
        }

        for (String dependency : dependencies) {
            System.out.println("- " + dependency);
        }
    }
}
