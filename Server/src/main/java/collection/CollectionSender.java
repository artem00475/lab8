package collection;

import Messages.CollectionInfo;
import RecievedMessages.ClientInfo;
import person.Person;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CollectionSender {
    private List<ClientInfo> clientInfos;
    private DatagramSocket datagramSocket;

    public CollectionSender(DatagramSocket datagramSocket) {
        clientInfos=new ArrayList<>();
        this.datagramSocket=datagramSocket;
    }

    public void addClient(ClientInfo clientInfo) {
        clientInfos.add(clientInfo);
    }

    public boolean checkClient(String login) {
        for (ClientInfo clientInfo : clientInfos) {
            if (clientInfo.getLogin().equals(login)) {
                return true;
            }
        }return false;
    }

    public void removeClient(String login) {
        for (ClientInfo clientInfo : clientInfos) {
            if (clientInfo.getLogin().equals(login)) {
                clientInfos.remove(clientInfo);
            }
        }
    }

    public void sendCollection(BlockingQueue<Person> people) {
        try {
            for (ClientInfo clientInfo : clientInfos) {
                byte[] buffer = serialize(new CollectionInfo(people));
                DatagramPacket output = new DatagramPacket(buffer, buffer.length, clientInfo.getInetAddress(),clientInfo.getPort());
                datagramSocket.send(output);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess = out.toByteArray();
        out.close();
        os.close();
        return outMess;
    }
}
