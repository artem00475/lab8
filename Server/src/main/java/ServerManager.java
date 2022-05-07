import Messages.Answer;
import Messages.Request;
import collection.CollectionManager;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.*;


public class ServerManager {
    private SendManager sendManager;
    private RecieveManager recieveManager;
    private CollectionManager collectionManager;
    private ServerCommandManager serverCommandManager;
    private ServerConsoleCommandManager serverConsoleCommandManager;
    private Scanner scanner;
    private Logger LOG;

    public ServerManager(SendManager sendManager,RecieveManager recieveManager, CollectionManager collectionManager) throws IOException {
        this.collectionManager=collectionManager;
        this.recieveManager=recieveManager;
        this.sendManager=sendManager;
        serverCommandManager = new ServerCommandManager(collectionManager);
        scanner = new Scanner(System.in);
        serverConsoleCommandManager = new ServerConsoleCommandManager(scanner,serverCommandManager);
        LOG = Logger.getLogger(ServerManager.class.getName());
        FileHandler fileHandler = new FileHandler("Application_log",true);
        fileHandler.setFormatter(new SimpleFormatter());
        LOG.addHandler(fileHandler);

    }

    public void run() {
        LOG.log(Level.INFO,"Сервер запущен");
        while (true) {
            try {
                Request request;
                try {
                    request = recieveManager.recieveRequest();
                    LOG.log(Level.INFO,"Получен запрос от клиента на выполнение команды "+request.getCommand().getName());
                    Answer answer = serverCommandManager.execute(request, false);
                    sendManager.sendAnswer(answer);
                    LOG.log(Level.INFO,"Команда "+request.getCommand().getName()+" выполнена и ответ отправлен клиенту");
                } catch (SocketTimeoutException exception) {
                    try {
                        if (System.in.available() > 0) {
                            String com = scanner.nextLine().toLowerCase(Locale.ROOT);
                            if (com.equals("exit")) {
                                collectionManager.saveCollection();
                                LOG.log(Level.INFO,"Коллекция сохранена в файл");
                                LOG.log(Level.INFO,"Завершение работы сервера");
                                break;
                            } else if (com.equals("save")) {
                                collectionManager.saveCollection();
                                LOG.log(Level.INFO,"Коллекция сохранена в файл");
                            } else {
                                try {
                                    if (serverConsoleCommandManager.run(com)) LOG.log(Level.INFO, "Выполнена команда "+com+" из консоли");
                                }catch (NoSuchElementException e){
                                    System.out.println("Вы вышли из ввода команды");
                                    scanner = new Scanner(System.in);
                                    serverConsoleCommandManager = new ServerConsoleCommandManager(scanner,serverCommandManager);
                                }
                            }
                        }
                    } catch (NullPointerException | IOException e) {
                    }
                } catch (IOException | ClassNotFoundException e) {
                    sendManager.sendAnswer(new Answer(null, true));
                    LOG.log(Level.WARNING,e.getMessage());
                }
            }catch (NoSuchElementException e){
                collectionManager.saveCollection();
                LOG.log(Level.INFO,"Завершение работы сервера");
                break;}
        }
    }
}
