import Messages.Request;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SendManager {
    private InetSocketAddress inetSocketAddress;
    private DatagramChannel datagramChannel;
    private byte[] buff;
    public SendManager(InetSocketAddress inetSocketAddress, DatagramChannel datagramChannel){
        this.inetSocketAddress = inetSocketAddress;
        this.datagramChannel=datagramChannel;
    }
    public void send(Request request){
        try {
            buff = serialize(request);
            datagramChannel.send(ByteBuffer.wrap(buff),inetSocketAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] serialize(Serializable mess) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(mess);
        return out.toByteArray();
    }

}
