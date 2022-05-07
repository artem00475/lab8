import Messages.Answer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SendManager {
    private final DatagramSocket datagramSocket;
    private final RecieveManager recieveManager;

    public SendManager (DatagramSocket datagramSocket,RecieveManager recieveManager){
        this.datagramSocket = datagramSocket;
        this.recieveManager=recieveManager;
    }

    public void sendAnswer(Answer answer) {
        try {
            byte[] buffer = serialize(answer);
            DatagramPacket output = new DatagramPacket(buffer, buffer.length, recieveManager.getAdress(), recieveManager.getPort());
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
