import DataBase.DBManager;
import Messages.Answer;
import Messages.Request;
import Messages.StatusInfo;
import RecievedMessages.ClientInfo;
import RecievedMessages.RecievedMessage;
import collection.CollectionManager;
import collection.CollectionSender;
import exceptions.ConnectionException;

import java.io.IOException;
import java.net.DatagramSocket;
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
    private final CollectionSender collectionSender;
    private final DatagramSocket datagramSocket;

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
        datagramSocket = new DatagramSocket(4585);
        collectionSender = new CollectionSender(datagramSocket);

    }

    public void run() {
        AtomicBoolean work= new AtomicBoolean(false);
        LOG.log(Level.INFO,"???????????? ??????????????");
        if (dbManager.connect()) {
            LOG.info("???????? ???????????? ????????????????????");
            work.set(true);
        } else {
            LOG.info("?????????????????????? ?? ?????????? ???????????? ???? ??????????????????????");
            System.out.println("???????????????????? ?????????????????????????? ????????????");
            LOG.log(Level.INFO,"???????????????????? ???????????? ??????????????");

        }
        if (work.get()) {
            short initialisator = dbManager.initializeCollection();
            if (initialisator == 1) {
                LOG.info("???????????????? ???? ???????? ???????????? ?????????????????? ?? ??????????????????");
            } else if (initialisator == 0) {
                LOG.info("?? ???????? ???????????? ?????? ??????????????????");
            } else LOG.info("???????????? ?????? ???????????? ????");
            try {
                new Thread(() -> {
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
                                            LOG.log(Level.INFO, "?????????????????? ?????????????? " + com + " ???? ??????????????");
                                    } catch (NoSuchElementException e) {
                                        System.out.println("???? ?????????? ???? ?????????? ??????????????");
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
                }).start();
                ExecutorService executorServiceProcessing = Executors.newFixedThreadPool(4);
                List<RecievedMessage> recievedMessages  = Collections.synchronizedList(new ArrayList<>());
                    while (work.get()) {
                        try {
                                try {
                                    recievedMessages.add(recieveManager.recieveRequest());
                                    if (!recievedMessages.isEmpty()) {
                                        executorServiceProcessing.submit(() -> {
                                            RecievedMessage recievedMessage = recievedMessages.remove(0);
                                            Request request = recievedMessage.getRequest();
                                            if (recievedMessage.getRequest().getCommand() == null) {
                                                short checkedUser = dbManager.checkUser(request.getLogin(), request.getPassword(),request.isRegister());
                                                if (checkedUser == 1) {
                                                    if (request.getExit()==0) {
                                                        boolean exist = collectionSender.checkClient(request.getLogin());
                                                        if (!exist) {
                                                            LOG.info("?????????????? ???????????? ???? ?????????????????????? ???? ?????????????? " + request.getLogin());
                                                            LOG.info("???????????? ??????????????????????");
                                                            collectionSender.addClient(new ClientInfo(request.getLogin(), recievedMessage.getInetAddress(), recievedMessage.getPort()));
                                                            sendManager.sendAnswer(new Answer("?????????????????????? " + request.getLogin() + " ??????????????????", false), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                            collectionSender.sendCollectionToClient(collectionManager.getCollection(), StatusInfo.ADD,request.getLogin());
                                                        }else {
                                                            LOG.info("?????????????? ???????????? ???? ?????????????????????? ???? ?????????????? " + request.getLogin());
                                                            LOG.info("???????????? "+request.getLogin()+" ?????? ??????????????????????");
                                                            sendManager.sendAnswer(new Answer("?????????????????????? " + request.getLogin() + " ?????? ??????????????????", true), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                        }
                                                    }else {
                                                        LOG.info("???????????? "+request.getLogin()+" ???????????????? ????????????");
                                                        collectionSender.removeClient(request.getLogin());
                                                    }
                                                } else if (checkedUser == 0) {
                                                    LOG.info("?????????????? ???????????? ???? ??????????????????????");
                                                    LOG.info("???????????? "+request.getLogin()+" ??????????????????????????????");
                                                    sendManager.sendAnswer(new Answer("?????????????????????? " + request.getLogin() + " ??????????????????", false), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                } else if (checkedUser == 5 || checkedUser == -1) {
                                                    LOG.info("?????????????? ???????????? ???? ?????????????????????? ???? ?????????????? " + request.getLogin());
                                                    LOG.info("???????????? ???? ??????????????????????");
                                                    sendManager.sendAnswer(new Answer("?????????????????????? " + request.getLogin() + " ???? ??????????????????", true), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                }else if (checkedUser == 2) {
                                                    LOG.info("?????????????? ???????????? ???? ??????????????????????");
                                                    LOG.info("???????????? ???? ??????????????????????????????");
                                                    sendManager.sendAnswer(new Answer("?????????????????????? " + request.getLogin() + " ???? ??????????????????", true), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                }
                                            } else if (!(recievedMessage.getRequest().getCommand() == null)) {
                                                if (dbManager.checkUser(request.getLogin(), request.getPassword(),request.isRegister()) == 1) {
                                                    LOG.log(Level.INFO, "?????????????? ???????????? ???? ???????????????????? ?????????????? " + request.getCommand().getName() + " ???? ?????????????? " + request.getLogin());
                                                    Answer answer = serverCommandManager.execute(request, true);
                                                    if (!answer.getErrors()) {
                                                        if (request.getCommand().getName().equals("add") || request.getCommand().getName().equals("add")) {
                                                            collectionSender.sendCollection(answer.getPeople(), StatusInfo.ADD);
                                                        }else if(request.getCommand().getName().equals("update id")) {
                                                            collectionSender.sendCollection(answer.getPeople(), StatusInfo.UPDATE);
                                                        }else if (request.getCommand().getName().equals("remove_by_id") || request.getCommand().getName().equals("remove_head") || request.getCommand().getName().equals("remove_greater") || request.getCommand().getName().equals("clear")) {
                                                            collectionSender.sendCollection(answer.getPeople(), StatusInfo.REMOVE);
                                                        }
                                                    }
                                                    sendManager.sendAnswer(answer, recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                    LOG.log(Level.INFO, "?????????????? " + request.getCommand().getName() + " ?????????????????? ?? ?????????? ?????????????????? ?????????????? " + request.getLogin());
                                                } else {
                                                    LOG.log(Level.INFO, "?????????????? ???????????? ???? ???????????????????? ?????????????? " + request.getCommand().getName() + " ???? ?????????????????????????????????? ??????????????");
                                                    sendManager.sendAnswer(new Answer("?????????????? ???? ??????????????????, ???????????? ???? ??????????????????????", true), recievedMessage.getInetAddress(), recievedMessage.getPort());
                                                    LOG.info("?????????????? ???? ??????????????????, ???????????? ???? ??????????????????????");
                                                }
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
                    executorServiceProcessing.shutdown();
                    LOG.log(Level.INFO, "???????????????????? ???????????? ??????????????");
            } catch (ConnectionException ignored) {}
        }
    }
}
