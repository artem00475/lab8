import Application.App;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import static java.lang.System.in;

public class Client {
    public static SendManager sendManager;
    public static RecieveManager recieveManager;
    public static Scanner scanner;
    public static void main(String[] args) throws IOException {
        DatagramChannel client = DatagramChannel.open();
        InetSocketAddress reciever = new InetSocketAddress("localhost",4584);
        App.run(args);
        scanner = new Scanner(in);
        sendManager = new SendManager(reciever,client);
        recieveManager = new RecieveManager(client);
        ClientManager clientManager = new ClientManager(sendManager,recieveManager);
        clientManager.consoleMode();
        client.close();
    }
}
