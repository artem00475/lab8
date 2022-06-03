package Messages;

import person.Person;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class CollectionInfo implements Serializable {
    private BlockingQueue<Person> collection;
    private StatusInfo statusInfo;

    public CollectionInfo(BlockingQueue<Person> collection,StatusInfo statusInfo) {
        this.collection=collection;
        this.statusInfo=statusInfo;
    }

    public BlockingQueue<Person> getCollection() {return collection;}

    public StatusInfo getStatusInfo() {return statusInfo;}
}
