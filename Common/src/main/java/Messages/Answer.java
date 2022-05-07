package Messages;

import java.io.Serializable;

public class Answer extends Message implements Serializable {
    private final String string;
    private final boolean wasErrors;

    public Answer(String str, boolean wasErrors){
        this.string=str;
        this.wasErrors=wasErrors;
    }

    public String getString(){
        return string;
    }

    public boolean getErrors(){
        return wasErrors;
    }
}
