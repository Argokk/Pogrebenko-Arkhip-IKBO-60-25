import javax.swing.*;
import java.awt.*;
import java.io.IOError;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static void createWindow(String csvFilename,String arg){
        Map<String, String> dict = readCSV(csvFilename);
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
        textHandler(field, commandLine, window, dict);
        runScript(arg, commandLine, window, dict);

        window.add(new JScrollPane(commandLine), BorderLayout.CENTER);
        window.add(field, BorderLayout.SOUTH);

        window.pack();
        window.setVisible(true);
        field.requestFocusInWindow();
    }
    public static void runScript(String filename, JTextArea commandLine, JFrame window, Map<String, String> dict){
        try {
            Path path = Paths.get(filename);
            List<String> data = Files.readAllLines(path);
            for (int i = 0; i < data.toArray().length; i++){
                commandLine.append("> " + data.get(i) + "\n");
                try {
                    String expanded = parse(data.get(i));
                    String commandAns = commandHandler(expanded, window, dict);
                    if(commandAns == ""){
                        commandLine.append(
                                "Ошибка выполнения стартового скрипта \n"
                        );
                        return;
                    }
                    commandLine.append(commandAns  + "\n");
                } catch (IllegalArgumentException exception) {
                    commandLine.append(
                            "Ошибка: " + exception.getMessage() + "\n"
                    );
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String commandHandler(String command, JFrame window, Map<String, String> dict){
        Pattern pattern = Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)");
        Matcher matcher = pattern.matcher(command);
        if (!matcher.find()) {
            return "";
        }
        String p_command = matcher.group(1);

        switch (p_command){
            case "ls" -> {
                if("dir".equals(dict.get(command.substring(matcher.end()).trim()))){
                    for(String path: dict.keySet()){
                        if(path.startsWith(command.substring(matcher.end()).trim()) && "dir".equals(dict.get(path))){
                                p_command += "      " + path;
                        }
                    }
                    return p_command;
                }
                else{
                    return command;
                }
            }
            case "cd" ->{
                String target = command.substring(matcher.end()).trim();

                if (target.isEmpty()) return "cd";
                if ("dir".equals(dict.get(target))) return "cd " + target;

                return "";
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
    public static void textHandler(JTextField field, JTextArea commandLine, JFrame window, Map<String, String> dict) {
        field.addActionListener(e -> {
            String command = field.getText();
            field.setText("");

            commandLine.append("> " + command + "\n");

            try {
                String expanded = parse(command);
                String commandAns = commandHandler(expanded, window, dict);
                commandLine.append(commandAns + "\n");
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
            return command;
        }

        String name = matcher.group(1);
        String value = System.getenv(name);

        if (value == null) {
            throw new IllegalArgumentException("Переменная " + name + " не найдена");
        }

        return command.substring(0, matcher.start()) + value + command.substring(matcher.end());
    }
    public static Map<String, String> readCSV(String filename) {
        Path path = Paths.get(filename);
        List<String> data = List.of();

        Map<String, String> dict = new HashMap<>();
        try {
            data = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Неверное имя виртуальной файловой системы");
        }
        for (int i = 1; i < data.toArray().length; i++){
            String[] line = data.get(i).split(",", -1);
            dict.put(line[0], line[1]);
        }
        return dict;
    }
    public static void main(String[] args){
        if(args.length != 2){
            System.out.println("Введите два аргумента");
            return;
        }
        Path txtPath = Paths.get(args[1]);
        Path csvPath = Paths.get(args[0]);
        try{
            Files.readAllLines(txtPath);
        }
        catch(IOException e){
            System.err.println("Неверное имя текстового файла");
            return;
        }
        try{
            Files.readAllLines(csvPath);
        }
        catch (IOException e){
            System.err.println("Неверное имя виртуальной файловой системы");
        }
        SwingUtilities.invokeLater(()->createWindow(args[0], args[1]));
    }
}
