import java.nio.file.Path;

public class Main {
    private static final int EXPECTED_ARGUMENTS = 1;
    private static final int WRONG_ARGUMENTS_CODE = 1;
    private static final int CONFIGURATION_ERROR_CODE = 2;

    public static void main(String[] args) {
        if (args.length != EXPECTED_ARGUMENTS) {
            System.err.println("Usage: java Main <path-to-config.xml>");
            System.exit(WRONG_ARGUMENTS_CODE);
        }

        try {
            AppConfig config = AppConfig.fromXml(Path.of(args[0]));
            config.print();
        } catch (ConfigurationException e) {
            printErrors(e);
            System.exit(CONFIGURATION_ERROR_CODE);
        }
    }

    private static void printErrors(ConfigurationException exception) {
        System.err.println("Configuration errors:");

        for (String error : exception.errors()) {
            System.err.println("- " + error);
        }
    }
}
