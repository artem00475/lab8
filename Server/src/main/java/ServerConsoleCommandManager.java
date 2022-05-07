import Messages.Request;
import commands.*;
import java.util.*;

public class ServerConsoleCommandManager {
    private Scanner scanner;
    private ConsoleCommandManager consoleCommandManager;
    private Command[] commands = {
            new AddCommand(),
            new InfoCommand(),
            new ShowCommand(),
            new HelpCommand(),
            new UpdateCommand(),
            new RemoveByIdCommand(),
            new ClearCommand(),
            new RemoveHeadCommand(),
            new AddIfMaxCommand(),
            new RemoveGreaterCommand(),
            new CountGreaterThanLocationCommand(),
            new PrintFieldAscendingLocationCommand(),
            new FilterLessThanEyeColorCommand(),
            new ExcecuteCommand()
    };
    private Deque<String> scriptQueue = new LinkedList<>();
    private boolean ifConsole;
    private ServerCommandManager serverCommandManager;

    public ServerConsoleCommandManager(Scanner scanner,ServerCommandManager serverCommandManager) {
        this.scanner = scanner;
        consoleCommandManager = new ConsoleCommandManager(scriptQueue, scanner);
        this.serverCommandManager=serverCommandManager;
    }

    public boolean run(String com) {
        ifConsole = true;
        boolean found = false;
        for (Command command : commands) {
            if (com.equals(command.getName())) {
                found = true;
                Request request = consoleCommandManager.execute(new Request(command), ifConsole);
                if (request != null) {
                    System.out.println(serverCommandManager.execute(request,false).getString());
                }
                if (com.equals("execute_script")) {
                    scriptMode();
                    ifConsole = true;
                }
            }
        }
        if (!found) {
            System.out.println("Команда введениа неверно, или такой команды не существует");
        }return found;
    }

    public void scriptMode() {
        ifConsole = false;
        while (!scriptQueue.isEmpty()) {
            String com = scriptQueue.removeFirst().toLowerCase(Locale.ROOT);
            if (com.equals("stop")) {
                System.out.println("Скрипт выполнен");
                break;
            } else {
                for (Command command : commands) {
                    if (com.equals(command.getName())) {
                        Request request;
                        if (!command.hasArgement()) {
                            request = consoleCommandManager.execute(new Request(command),true);
                        } else {
                            request =  consoleCommandManager.execute(new Request(command),ifConsole);
                        }if (request != null) {
                            System.out.println(serverCommandManager.execute(request,false).getString());
                        }
                    }
                }
            }
        }
    }
}




