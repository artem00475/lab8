import collection.CollectionManager;
import java.io.IOException;
import java.net.DatagramSocket;
import java.sql.*;


public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket server = new DatagramSocket(4584);
        RecieveManager recieveManager = new RecieveManager(server);
        SendManager sendManager = new SendManager(server,recieveManager);
        ServerManager serverManager = new ServerManager(sendManager,recieveManager);
        serverManager.run();
        server.close();
    }
}
