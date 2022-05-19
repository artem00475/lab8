package commands;

import Messages.Request;
import Script.*;
import java.util.Deque;
import java.util.Scanner;

public class ConsoleCommandManager implements CommandManager {
    ConsoleManager consoleManager = new ConsoleManager();
    private final ScriptManager scriptManager;
    private final Scanner scanner;
    private String login;
    private int password;

    public ConsoleCommandManager(Deque<String> stringDeque,Scanner scanner,String login,int password) {
        scriptManager = new ScriptManager(stringDeque);
        this.scanner = scanner;
        this.login=login;
        this.password=password;
    }

    public Request execute(Request request, boolean ifConsole) {
        Command command = request.getCommand();
        if (ifConsole) {
            if (!command.hasArgement()) {
                return new Request(command,login,password);
            } else {
                if (command.getName().equals("add") || command.getName().equals("add_if_max") || command.getName().equals("remove_greater")) {
                    return new Request(command,consoleManager.getPersonFromConsole(scanner),login,password);
                } else if (command.getName().equals("remove_by_id")) {
                    return new Request(command,consoleManager.getID(scanner),login,password);
                } else if (command.getName().equals("update id")) {
                    return new Request(command,consoleManager.getID(scanner),consoleManager.getPersonFromConsole(scanner),login,password);
                } else if (command.getName().equals("count_greater_than_location")) {
                    return new Request(command,consoleManager.getLocationFromConsole(scanner),login,password);
                } else if (command.getName().equals("filter_less_than_eye_color")) {
                    return new Request(command,consoleManager.getEyeColor(scanner),login,password);
                } else if (command.getName().equals("execute_script")) {
                    scriptManager.createScriptFlesArray();
                    scriptManager.addFile(consoleManager.getFile(scanner));
                }
            }
        } else {
            if (command.getName().equals("add") || command.getName().equals("add_if_max") || command.getName().equals("remove_greater")) {
                return new Request(command,scriptManager.getPersonFromScript(),login,password);
            }else if (command.getName().equals("remove_by_id")) {
                return new Request(command,scriptManager.getID(),login,password);
            }else if (command.getName().equals("update id")) {
                return new Request(command,scriptManager.getID(),scriptManager.getPersonFromScript(),login,password);
            }else if (command.getName().equals("count_greater_than_location")) {
                return new Request(command,scriptManager.getLocationFromScript(),login,password);
            }else if (command.getName().equals("filter_less_than_eye_color")) {
                return new Request(command,scriptManager.getEyeColor(),login,password);
            }else if (command.getName().equals("execute_script")) {
                scriptManager.addFile(scriptManager.getFile());
            }
        }return null;
    }
}
