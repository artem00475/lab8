package Client;

import Messages.Answer;
import Messages.Request;
import Requests.RecieveManager;
import Requests.SendManager;
import commands.*;
import exceptions.ConnectionException;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

public class ClientManager {
    private final Command[] commands = {
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
    private ConsoleCommandManager clientCommandManager;
    private final Deque<String> scriptQueue = new LinkedList<>();
    private final SendManager sendManager;
    private final RecieveManager recieveManager;
    private String login;
    private String password;
    private TableManager tableManager;

    public ClientManager( SendManager sendManager, RecieveManager recieveManager, TableManager tableManager) {
        this.sendManager=sendManager;
        this.recieveManager=recieveManager;
        this.tableManager=tableManager;
    }

    public boolean signUp (String login, String password) {return sendAndRecieve(new Request(login, password,true)).getErrors();}

    public boolean signIn (String login, String password) {
        boolean error = sendAndRecieve1(new Request(login, password, false)).getErrors();
        if (!error) {
            tableManager.chahgeAdres();
            tableManager.begin();
        }
        return error;}

    public void setUser(String login,String password) {
        this.login=login;
        this.password=password;
        clientCommandManager = new ConsoleCommandManager(scriptQueue,new Scanner(System.in),login,password);
    }

    public void exit() {
        sendManager.send(new Request(login,password,new Short("1")));
    }

    public Answer consoleMode(Request request) {
        return sendAndRecieve(request);
//                for (Command command : commands) {
//                    if (com.equals(command.getName())) {
//                        if (!command.hasArgement()) {
//                            return sendAndRecieve(new Request(command,login,password));
//                        }else if (com.equals("execute_script")) {
//                            scriptMode();
//                            ifConsole = true;
//                            return null;
//                        }
//                    }
//                }return null;
            }

//        try {
//            clientCommandManager = new ConsoleCommandManager(scriptQueue, Client.scanner, login, password);
//            ifConsole = true;
//            while (true) {
//                boolean found = false;
//                System.out.print("?????????????? ?????????????? (help - ???????????? ????????????): ");
//                String com;
//                try {
//                    com = Client.scanner.nextLine().toLowerCase(Locale.ROOT);
//                } catch (NoSuchElementException e) {
//                    System.out.println("\n???? ?????????? ???? ??????????????????");
//                    break;
//                }
//                if (com.equals("exit")) {
//                    break;
//                } else {
//                    try {
//                        for (Command command : commands) {
//                            if (com.equals(command.getName())) {
//                                found = true;
//                                Request request = clientCommandManager.execute(new Request(command), ifConsole);
//                                if (request != null) {
//                                    sendAndRecieve(request);
//                                }
//                                if (com.equals("execute_script")) {
//                                    scriptMode();
//                                    ifConsole = true;
//                                }
//                            }
//                        }
//                    } catch (NoSuchElementException e) {
//                        Client.scanner = new Scanner(System.in);
//                        clientCommandManager = new ConsoleCommandManager(scriptQueue, Client.scanner, login, password);
//                        System.out.println("???? ?????????? ???? ?????????? ?????????????? ??????????????");
//                    } catch (ConnectionException e) {
//                        System.out.println("?????????????????? ?????????????? ??????????");
//                    }
//                    if (!found) {
//                        System.out.println("?????????????? ???????????????? ??????????????, ?????? ?????????? ?????????????? ???? ????????????????????");
//                    }
//                }
//            }
//        }catch (ConnectionException e) {
//            System.out.println("?????????????????? ?????????????? ??????????");
//        }


    public Answer scriptMode(File file) {
        clientCommandManager.getScriptManager(file);
        boolean ifConsole = false;
        int c = 0;
        while (!scriptQueue.isEmpty()) {
            String com = scriptQueue.removeFirst().toLowerCase(Locale.ROOT);
            if (com.equals("stop") & c>0) {
                return new Answer("???????????? ????????????????",false);
            } else {
                for (Command command : commands) {
                    if (com.equals(command.getName())) {
                        c++;
                        Request request;
                        if (!command.hasArgement()) {
                            request = clientCommandManager.execute(new Request(command),true);
                        } else {
                            request =  clientCommandManager.execute(new Request(command), ifConsole);
                        }if (request != null) {
                            System.out.println(sendAndRecieve(request).getString());
                        }
                    }
                }
            }
        }return null;
    }

    public Answer sendAndRecieve(Request request){
        try {
            sendManager.send(request);
            return recieveManager.recieve();
        }catch (SocketTimeoutException e) {
            System.out.println("???????????? ???? ????????????????, ?????????????????? ?????????????? ?????????????????? ????????????...");
            try {
                sendManager.send(request);
                System.out.println(recieveManager.recieve().getString());
                return recieveManager.recieve();
            }catch (IOException | ClassNotFoundException exception){
                throw new ConnectionException("???????????? ???? ????????????????");
            }
        } catch (IOException | ClassNotFoundException e){
            throw new ConnectionException("??????-???? ?????????? ???? ??????");
        }
    }

    public Answer sendAndRecieve1(Request request){
        try {
            tableManager.send(request);
            return (Answer) tableManager.recieve();
        }catch (SocketTimeoutException e) {
            System.out.println("???????????? ???? ????????????????, ?????????????????? ?????????????? ?????????????????? ????????????...");
            try {
                tableManager.send(request);
                System.out.println(recieveManager.recieve().getString());
                return (Answer) tableManager.recieve();
            }catch (IOException | ClassNotFoundException exception){
                throw new ConnectionException("???????????? ???? ????????????????");
            }
        } catch (IOException | ClassNotFoundException e){
            throw new ConnectionException("??????-???? ?????????? ???? ??????");
        }
    }
}
