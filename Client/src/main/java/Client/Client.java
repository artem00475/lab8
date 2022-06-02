package Client;

import Application.App;
import Requests.RecieveManager;
import Requests.SendManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import person.Person;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

import static java.lang.System.in;

public class Client {
    public static SendManager sendManager;
    public static RecieveManager recieveManager;
    public static Scanner scanner;
    public static void main(String[] args) throws IOException {
        DatagramChannel client = DatagramChannel.open();
        InetSocketAddress reciever = new InetSocketAddress("localhost",4584);
        scanner = new Scanner(in);
        sendManager = new SendManager(reciever,client);
        recieveManager = new RecieveManager(client);
        SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty(true);
        ObservableList<Person> people = FXCollections.observableArrayList();
        TableManager tableManager = new TableManager(simpleBooleanProperty,people);
        App.setRequestManagers(recieveManager,sendManager,tableManager);
        App.run(args,people);
        simpleBooleanProperty.set(false);
        tableManager.stop();
        App.exit();
        client.close();
    }
}
