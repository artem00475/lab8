import DataBase.DBManager;
import Messages.Answer;
import Messages.Request;
import collection.CollectionManager;
import exceptions.ConnectionException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.*;


public class ServerManager {
    private final SendManager sendManager;
    private final RecieveManager recieveManager;
    private final CollectionManager collectionManager;
    private final ServerCommandManager serverCommandManager;
    private ServerConsoleCommandManager serverConsoleCommandManager;
    private Scanner scanner;
    private final Logger LOG;
    private DBManager dbManager;

    public ServerManager(SendManager sendManager,RecieveManager recieveManager) throws IOException {
        this.recieveManager=recieveManager;
        this.sendManager=sendManager;
        dbManager = new DBManager();
        this.collectionManager=new CollectionManager(dbManager);
        serverCommandManager = new ServerCommandManager(collectionManager);
        scanner = new Scanner(System.in);
        serverConsoleCommandManager = new ServerConsoleCommandManager(scanner,serverCommandManager);
        LOG = Logger.getLogger(ServerManager.class.getName());
        FileHandler fileHandler = new FileHandler("Application_log",true);
        fileHandler.setFormatter(new SimpleFormatter());
        LOG.addHandler(fileHandler);

    }

    public void run() {
        boolean work;
        LOG.log(Level.INFO,"Сервер запущен");
        if (dbManager.connect()) {
            LOG.info("База данных подключена");
            work =true;
        } else {
            LOG.info("Подключение с базой данных не установлено");
            System.out.println("Попробуйте перезапустить сервер");
            LOG.log(Level.INFO,"Завершение работы сервера");
            work=false;
        }
        short initialisator =  dbManager.initializeCollection();
        if (initialisator == 1) {
            LOG.info("Элементы из базы данных добавлены в коллекцию");
        }else if (initialisator==0) {
            LOG.info("В базе данных нет элементов");
        } else LOG.info("Ошибка при чтении БД");
        try {
            while (work) {
                try {
                    Request request;
                    try {
                        request = recieveManager.recieveRequest();
                        LOG.log(Level.INFO, "Получен запрос от клиента на выполнение команды " + request.getCommand().getName());
                        Answer answer = serverCommandManager.execute(request, false);
                        sendManager.sendAnswer(answer);
                        LOG.log(Level.INFO, "Команда " + request.getCommand().getName() + " выполнена и ответ отправлен клиенту");
                    } catch (SocketTimeoutException exception) {
                        try {
                            if (System.in.available() > 0) {
                                String com = scanner.nextLine().toLowerCase(Locale.ROOT);
                                if (com.equals("exit")) {
                                    LOG.log(Level.INFO, "Завершение работы сервера");
                                    break;
                            } else {
                                    try {
                                        if (serverConsoleCommandManager.run(com))
                                            LOG.log(Level.INFO, "Выполнена команда " + com + " из консоли");
                                    } catch (NoSuchElementException e) {
                                        System.out.println("Вы вышли из ввода команды");
                                        scanner = new Scanner(System.in);
                                        serverConsoleCommandManager = new ServerConsoleCommandManager(scanner, serverCommandManager);
                                    }catch (Exception e){}
                                }
                            }
                        } catch (NullPointerException | IOException ignored) {
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        sendManager.sendAnswer(new Answer(null, true));
                        LOG.log(Level.WARNING, e.getMessage());
                    }
                } catch (NoSuchElementException e) {
                    LOG.log(Level.INFO, "Завершение работы сервера");
                    break;
                }
            }
        }catch (ConnectionException e){}
    }
}
