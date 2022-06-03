package Messages;

import person.Person;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Answer extends Message implements Serializable {
    private final String string;
    private final boolean wasErrors;
    private BlockingQueue<Person> people = null;

    public Answer(String str, boolean wasErrors){
        this.string=str;
        this.wasErrors=wasErrors;
    }

    public Answer(String str, boolean wasErrors,BlockingQueue<Person> people) {
        this.string=str;
        this.wasErrors=wasErrors;
        this.people=people;
    }


    public String getString(){
        return string;
    }

    public boolean getErrors(){
        return wasErrors;
    }

    public BlockingQueue<Person> getPeople() {return people;}
}
