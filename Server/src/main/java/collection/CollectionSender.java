package collection;

import Messages.Answer;
import Messages.CollectionInfo;
import RecievedMessages.ClientInfo;
import person.Person;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
