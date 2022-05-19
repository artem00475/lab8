package Messages;

import commands.Command;
import commands.CommandManager;

import java.io.Serializable;

public class Request extends Message implements Serializable {
    private Command command = null;
    private int id= -1;
    private Object object=null;
    private String login;
    private int password;

    public Request(Command name) {
        this.command=name;
        this.login=null;
        this.password=-1;
    }
    public Request(String login,int password){
        this.login=login;
        this.password=password;
    }
    public Request(Command name, String login, int password){
        this.login=login;
        this.command=name;
        this.password=password;
    }
    public Request(Command name, int id, Object object, String login, int password){
        this.id=id;
        this.command=name;
        this.object=object;
        this.login=login;
        this.password=password;
    }
    public Request(Command name, Object object,String login, int password){
        this.command=name;
        this.object=object;
        this.login=login;
        this.password=password;
    }
    public Request(Command name, int id, String login, int password){
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

    public int getPassword() {return password;}

    public String getLogin() {return login;}

}
