package Messages;

import commands.Command;
import commands.CommandManager;

import java.io.Serializable;
import java.net.InetAddress;

public class Request extends Message implements Serializable {
    private Command command = null;
    private int id= -1;
    private Object object=null;
    private String login;
    private String password;
    private boolean register=false;
    private InetAddress inetAddress = null;
    private int port = 0;

    public Request(Command name) {
        this.command=name;
        this.login=null;
        this.password="";
    }
    public Request(String login,String password,boolean register){
        this.login=login;
        this.password=password;
        this.register=register;
    }

    public Request(String login,String password,InetAddress inetAddress,int port) {
        this.login=login;
        this.password=password;
        this.inetAddress=inetAddress;
        this.port=port;
    }
    public Request(Command name, String login, String password){
        this.login=login;
        this.command=name;
        this.password=password;
    }
    public Request(Command name, int id, Object object, String login, String password){
        this.id=id;
        this.command=name;
        this.object=object;
        this.login=login;
        this.password=password;
    }
    public Request(Command name, Object object,String login, String password){
        this.command=name;
        this.object=object;
        this.login=login;
        this.password=password;
    }
    public Request(Command name, int id, String login, String password){
        this.id=id;
        this.command=name;
        this.login=login;
        this.password=password;
    }

    public Command getCommand(){
        return command;
    }

    public Object getObject(){
        return object;
    }

    public int getId(){
        return id;
    }

    public String getPassword() {return password;}

    public String getLogin() {return login;}

    public boolean isRegister() {return register;}

    public InetAddress getInetAddress() {return inetAddress;}

    public int getPort() {return port;}

}
