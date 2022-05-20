import Messages.Answer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendManager {
    private final DatagramSocket datagramSocket;
    private final RecieveManager recieveManager;

    public SendManager (DatagramSocket datagramSocket,RecieveManager recieveManager){
        this.datagramSocket = datagramSocket;
        this.recieveManager=recieveManager;
    }

    public void sendAnswer(Answer answer, InetAddress inetAddress,int port) {
        try {
            byte[] buffer = serialize(answer);
            DatagramPacket output = new DatagramPacket(buffer, buffer.length, inetAddress, port);
            datagramSocket.send(output);
        }catch (IOException ignored){}
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
