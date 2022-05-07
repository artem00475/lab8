package Messages;

import commands.Command;

import java.io.Serializable;

public class Request extends Message implements Serializable {
    private final Command command;
    private int id= -1;
    private Object object=null;
    public Request(Command name){
        this.command=name;
    }
    public Request(Command name, int id, Object object){
        this.id=id;
        this.command=name;
        this.object=object;
    }
    public Request(Command name, Object object){
        this.command=name;
        this.object=object;
    }
    public Request(Command name, int id){
        this.id=id;
        this.command=name;
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

}
