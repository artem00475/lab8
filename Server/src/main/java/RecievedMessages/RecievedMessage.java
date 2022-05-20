package RecievedMessages;

import Messages.Request;

import java.net.InetAddress;

public class RecievedMessage {
    private Request request;
    private InetAddress inetAddress;
    private int port;

    public RecievedMessage(Request request,InetAddress inetAddress,int port) {
        this.request=request;
        this.inetAddress=inetAddress;
        this.port=port;
    }

    public Request getRequest() {return request;}

    public InetAddress getInetAddress() {return inetAddress;}

    public int getPort() {return port;}
}
