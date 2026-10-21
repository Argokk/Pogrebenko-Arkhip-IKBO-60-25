import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Format;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static String getUserName(){return System.getProperty("user.name");}
    public static String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e){
            return "Unknown";
        }
    }
    public static void createWindow(){

        String username = getUserName();
        String hostName = getHostName();
        String title = String.format("Эмулятор - [%s@%s]", username, hostName);

        JFrame window = new JFrame();
        window.setTitle(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setPreferredSize(new Dimension(1000,800));
        window.setVisible(true);

        JTextArea commandLine = new JTextArea();
        commandLine.setBackground(Color.BLACK);
        commandLine.setForeground(Color.WHITE);
        commandLine.setCaretColor(Color.WHITE);
        commandLine.setVisible(true);
        commandLine.setEditable(false);

        JTextField field = new JTextField();
        textHandler(field, commandLine, window);

        window.add(new JScrollPane(commandLine), BorderLayout.CENTER);
        window.add(field, BorderLayout.SOUTH);

        window.pack();
        window.setVisible(true);
        field.requestFocusInWindow();
    }
    public static String commandHandler(String command, JFrame window){
        Pattern pattern = Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) {
            return "";
        }
        String p_command = matcher.group(1);
        switch (p_command){
            case "ls" -> {
                return p_command;
            }
            case "cd" -> {
                return p_command;
            }
            case "exit" -> {
                window.dispose();
                return "";
            }
            default -> {
                return "";
            }
        }
    }
    public static void textHandler(JTextField field, JTextArea commandLine, JFrame window) {
        field.addActionListener(e -> {
            String command = field.getText();
            field.setText("");

            commandLine.append("> " + command + "\n");

            try {
                String commandAns = commandHandler(command, window);
                String expanded = parse(command);
                commandLine.append(commandAns + ' ' + expanded + "\n");
            } catch (IllegalArgumentException exception) {
                commandLine.append(
                        "Ошибка: " + exception.getMessage() + "\n"
                );
            }
        });
    }
    public static String parse(String command) {
        Pattern pattern = Pattern.compile("\\$([A-Za-z_][A-Za-z0-9_]*)");
        Matcher matcher = pattern.matcher(command);

        if (!matcher.find()) {
            return "";
        }

        String name = matcher.group(1);
        String value = System.getenv(name);

        if (value == null) {
            throw new IllegalArgumentException("Переменная " + name + " не найдена");
        }

        return value + command.substring(matcher.end());
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::createWindow);
    }
}
