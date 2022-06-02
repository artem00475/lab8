package Client;

import Application.MapManager;
import Messages.CollectionInfo;
import Messages.Request;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import person.Person;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.DatagramChannel;

public class TableManager {
    private DatagramChannel datagramChannel;
    private InetSocketAddress inetSocketAddress;
    private ObservableList<Person> people;
    private Thread thread;
    private SimpleBooleanProperty work;

    public TableManager(SimpleBooleanProperty simpleBooleanProperty, ObservableList<Person> people) {
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inetSocketAddress = new InetSocketAddress("localhost",4584);
        work = new SimpleBooleanProperty();
        work.bind(simpleBooleanProperty);
        this.people=people;
        thread= new Thread(() -> {
            run();
        });
    }

    public DatagramChannel getDatagramChannel() {return datagramChannel;}

    public void begin() {thread.start();}

    public void chahgeAdres() {
        inetSocketAddress= new InetSocketAddress("localhost",4585);
    }

    public void run() {
        while (work.get()) {
            try {
                CollectionInfo collectionInfo = (CollectionInfo) recieve();
                people.clear();
                people.addAll(collectionInfo.getCollection());
                MapManager.drawPersons(collectionInfo.getCollection());
            }catch (AsynchronousCloseException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void stop() {
        try {
            datagramChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }

    public void send(Request request){
        try {
            byte[] buff = serialize(request);
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

    public Object recieve() throws IOException, ClassNotFoundException {
        byte[] buffer = new byte[1024*1024];
        DatagramPacket datagramPacket = new DatagramPacket(buffer,buffer.length);
        datagramChannel.socket().receive(datagramPacket);
        byte[] bytes = datagramPacket.getData();
        return deserialize(bytes);
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }


}
