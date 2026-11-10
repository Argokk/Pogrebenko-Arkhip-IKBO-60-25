import java.util.List;


public class ConfigurationException extends Exception {
    private final List<String> errors;

    public ConfigurationException(List<String> errors) {
        this.errors = List.copyOf(errors);
    }

    public List<String> errors() {
        return errors;
    }
}
