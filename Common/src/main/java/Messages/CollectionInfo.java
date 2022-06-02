package Messages;

import person.Person;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class CollectionInfo implements Serializable {
    private BlockingQueue<Person> collection;

    public CollectionInfo(BlockingQueue<Person> collection) {
        this.collection=collection;
    }

    public BlockingQueue<Person> getCollection() {return collection;}
}
