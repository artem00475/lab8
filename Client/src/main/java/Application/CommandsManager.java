package Application;

import Client.ClientManager;
import Messages.Answer;
import Messages.Request;
import commands.*;
import exceptions.ConnectionException;
import javafx.scene.control.*;
import person.ColorE;
import person.Location;
import person.Person;

import java.io.File;

public class CommandsManager {
    private ClientManager clientManager;
    private PersonCreatorController personCreatorController;
    private Person person;
    private String login;
    private String password;

    public CommandsManager(ClientManager clientManager, PersonCreatorController personCreatorController) {
        this.clientManager=clientManager;
        this.personCreatorController=personCreatorController;
    }

    public void setUser(String login,String password) {
        this.login=login;
        this.password=password;
    }

    public void help() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Help");
            alert.setContentText(clientManager.consoleMode(new Request(new HelpCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Info");
            alert.setContentText(clientManager.consoleMode(new Request(new InfoCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void print() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Print field ascending location");
            alert.setContentText(clientManager.consoleMode(new Request(new PrintFieldAscendingLocationCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void add() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            person=personCreatorController.getPerson();
            if (person!=null) {
                alert.setTitle("Add");
                alert.setContentText(clientManager.consoleMode(new Request(new AddCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void addIfMax() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            person=personCreatorController.getPerson();
            if (person!=null) {
                alert.setTitle("Add If Max");
                alert.setContentText(clientManager.consoleMode(new Request(new AddIfMaxCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void removeById() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Remove by id");
            alert.setContentText(clientManager.consoleMode(new Request(new RemoveByIdCommand(),personCreatorController.getId(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException e){}
    }

    public void updateById() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        int id;
        try {
            alert.setTitle("Update");
            id = personCreatorController.getId();
            person=personCreatorController.getPerson();
            if (person!=null) {
                alert.setContentText(clientManager.consoleMode(new Request(new UpdateCommand(), id,person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException e){}
    }

    public void removeHead() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Remove head");
            alert.setContentText(clientManager.consoleMode(new Request(new RemoveHeadCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void removeGreater() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            person=personCreatorController.getPerson();
            if (person!=null) {
                alert.setTitle("Remove Greater");
                alert.setContentText(clientManager.consoleMode(new Request(new RemoveGreaterCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void clear() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle("Clear");
            alert.setContentText(clientManager.consoleMode(new Request(new ClearCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void filterEyeColor() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        ColorE colorE = personCreatorController.getEyeColor();
        if (colorE!=null) {
            try {
                alert.setTitle("Filter less than eye color");
                alert.setContentText(clientManager.consoleMode(new Request(new FilterLessThanEyeColorCommand(), colorE, login, password)).getString());
                alert.showAndWait();
            } catch (ConnectionException e) {
                alert.setContentText("Try later");
                alert.showAndWait();
                throw new ConnectionException("");
            }
        }
    }

    public void countLocation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        Location location = personCreatorController.getLocation();
        if (location!=null) {
            try {
                alert.setTitle("Count greater than location");
                alert.setContentText(clientManager.consoleMode(new Request(new CountGreaterThanLocationCommand(),location , login, password)).getString());
                alert.showAndWait();
            } catch (ConnectionException e) {
                alert.setContentText("Try later");
                alert.showAndWait();
                throw new ConnectionException("");
            }
        }
    }

    public void script() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Script");
        File file = personCreatorController.getFile();
        if (file!=null) {
            try {
                Answer answer = clientManager.scriptMode(file);
                if (answer!=null) {
                    alert.setContentText(answer.getString());
                }else alert.setContentText("Скрипт не найден");
                alert.showAndWait();
            }catch (ConnectionException e) {
                alert.setContentText("Try later");
                alert.showAndWait();
                throw new ConnectionException("");
            }
        }
    }
}