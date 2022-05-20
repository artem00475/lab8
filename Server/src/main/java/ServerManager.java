import DataBase.DBManager;
import Messages.Answer;
import Messages.Request;
import collection.CollectionManager;
import exceptions.ConnectionException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.*;


public class ServerManager {
    private final SendManager sendManager;
    private final RecieveManager recieveManager;
    private final CollectionManager collectionManager;
    private final ServerCommandManager serverCommandManager;
    private ServerConsoleCommandManager serverConsoleCommandManager;
    private Scanner scanner;
    private final Logger LOG;
    private final DBManager dbManager;

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
        System.out.println(Runtime.getRuntime().availableProcessors());
        AtomicBoolean work= new AtomicBoolean(false);
        LOG.log(Level.INFO,"Сервер запущен");
        if (dbManager.connect()) {
            LOG.info("База данных подключена");
            work.set(true);
        } else {
            LOG.info("Подключение с базой данных не установлено");
            System.out.println("Попробуйте перезапустить сервер");
            LOG.log(Level.INFO,"Завершение работы сервера");

        }
        if (work.get()) {
            short initialisator = dbManager.initializeCollection();
            if (initialisator == 1) {
                LOG.info("Элементы из базы данных добавлены в коллекцию");
            } else if (initialisator == 0) {
                LOG.info("В базе данных нет элементов");
            } else LOG.info("Ошибка при чтении БД");
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                executor.submit(() -> {
                    while (true) {
                        try {
                            if (System.in.available() > 0) {
                                String com = scanner.nextLine().toLowerCase(Locale.ROOT);
                                if (com.equals("exit")) {
                                    work.set(false);
                                    break;
                                } else {
                                    try {
                                        dbManager.setUser("Server", "1");
                                        if (serverConsoleCommandManager.run(com))
                                            LOG.log(Level.INFO, "Выполнена команда " + com + " из консоли");
                                    } catch (NoSuchElementException e) {
                                        System.out.println("Вы вышли из ввода команды");
                                        scanner = new Scanner(System.in);
                                        serverConsoleCommandManager = new ServerConsoleCommandManager(scanner, serverCommandManager);
                                    } catch (Exception ignored) {
                                    }
                                }
                            }
                        } catch (NullPointerException | IOException ignored) {
                        } catch (NoSuchElementException e) {
                            work.set(false);
                            break;
                        }
                    }
                });
                ExecutorService executorServiceProcessing = Executors.newFixedThreadPool(3);
                    while (work.get()) {
                        Request request;
                        try {
                                try {
                                    request=recieveManager.recieveRequest();
                                if (request.getCommand() == null) {
                                    executorServiceProcessing.submit(() -> {
                                        LOG.info("Получен запрос на авторизацию от клиента "+request.getLogin());
                                        short checkedUser = dbManager.checkUser(request.getLogin(), request.getPassword());
                                        if (checkedUser == 1) {
                                            LOG.info("Клиент авторизован");
                                            sendManager.sendAnswer(new Answer("Авторизация "+request.getLogin()+" выполнена", false));
                                        } else if (checkedUser == 0) {
                                            LOG.info("Клиент зарегистрирован");
                                            sendManager.sendAnswer(new Answer("Регистрация "+request.getLogin()+" выполнена", false));
                                        } else if (checkedUser == -1) {
                                            LOG.info("Клиент не авторизован");
                                            sendManager.sendAnswer(new Answer("Авторизация "+request.getLogin()+" не выполнена", true));
                                        }
                                    });
                                } else if (!(request.getCommand() == null)) {
                                    executorServiceProcessing.submit(() -> {
                                        if (dbManager.checkUser(request.getLogin(), request.getPassword()) == 1) {
                                            LOG.log(Level.INFO, "Получен запрос на выполнение команды " + request.getCommand().getName() + " от клиента "+request.getLogin());
                                            Answer answer = serverCommandManager.execute(request, true);
                                            sendManager.sendAnswer(answer);
                                            LOG.log(Level.INFO, "Команда " + request.getCommand().getName() + " выполнена и ответ отправлен клиенту "+request.getLogin());
                                        } else {
                                            LOG.log(Level.INFO, "Получен запрос на выполнение команды " + request.getCommand().getName() + " от неавторизованного клиента");
                                            sendManager.sendAnswer(new Answer("Команда не выполнена, клиент не авторизован", true));
                                            LOG.info("Команда не выполнена, клиент не авторизован");
                                        }
                                    });
                                }
                                }catch (SocketTimeoutException ignored){
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                    LOG.log(Level.WARNING, e.getMessage());
                                }
                            } catch (RejectedExecutionException ignored) {}
                    }
                    executor.shutdown();
                    executorServiceProcessing.shutdown();
                    LOG.log(Level.INFO, "Завершение работы сервера");
            } catch (ConnectionException ignored) {}
        }
    }
}
