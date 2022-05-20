

import Messages.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RecieveManager {
    private int clientPort;
    private InetAddress clientAdress;
    private final DatagramSocket datagramSocket;
    public RecieveManager(DatagramSocket datagramSocket){
        this.datagramSocket=datagramSocket;
    }

    public Request recieveRequest() throws IOException, ClassNotFoundException {
        byte[] bufer = new byte[1024*1024];
        DatagramPacket packet = new DatagramPacket(bufer,bufer.length);
        datagramSocket.setSoTimeout(100);
        datagramSocket.receive(packet);
        clientAdress = packet.getAddress();
        clientPort=packet.getPort();
        return (Request) deserialize(packet.getData());
    }
    public int getPort(){
        return clientPort;
    }

    public InetAddress getAdress(){
        return clientAdress;
    }

    public static Serializable deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        Serializable request = (Serializable) is.readObject();
        in.close();
        is.close();
        return request;
    }
}
