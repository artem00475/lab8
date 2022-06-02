package RecievedMessages;

import java.net.InetAddress;

public class ClientInfo {
    private String login;
    private InetAddress inetAddress;
    private int port;

    public ClientInfo(String login,InetAddress inetAddress,int port) {
        this.inetAddress=inetAddress;
        this.login=login;
        this.port=port;
    }

    public String getLogin() {return login;}

    public InetAddress getInetAddress() {return inetAddress;}

    public int getPort() {return port;}
}
