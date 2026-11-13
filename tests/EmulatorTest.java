import java.util.HashMap;
import java.util.Map;

public class EmulatorTest {
    public static void main(String[] args) {
        Map<String, String> files = new HashMap<>();
        files.put("/", "dir");
        files.put("/docs", "dir");
        files.put("/docs/file", "file");
        check("cd /docs", Main.commandHandler("cd /docs", null, files));
        check("", Main.commandHandler("cd /missing", null, files));
        check("hello", Main.parse("hello"));
        check("ls      /docs", Main.commandHandler("ls /docs", null, files));
        System.out.println("PASS emulator commands");
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected: " + expected + "; actual: " + actual);
        }
    }
}
