package Application;

import Client.ClientManager;
import Language.Languages;
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
    private final ClientManager clientManager;
    private final PersonCreatorController personCreatorController;
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
            alert.setTitle(Languages.getString("helpCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new HelpCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void update(Person person1) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("update").get());
            person=personCreatorController.updatePerson(person1);
            if (person!=null) {
                alert.setContentText(clientManager.consoleMode(new Request(new UpdateCommand(), person1.getID(),person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException ignored){}
    }

    public void info() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("infoCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new InfoCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void print() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("printCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new PrintFieldAscendingLocationCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
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
                alert.setTitle(Languages.getString("addCommand").get());
                alert.setContentText(clientManager.consoleMode(new Request(new AddCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
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
                alert.setTitle(Languages.getString("addIfMaxCommand").get());
                alert.setContentText(clientManager.consoleMode(new Request(new AddIfMaxCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void remove(Person person) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("remove").get());
            alert.setContentText(clientManager.consoleMode(new Request(new RemoveByIdCommand(),person.getID(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException ignored){}
    }

    public void removeById() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("removeByIdCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new RemoveByIdCommand(),personCreatorController.getId(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException ignored){}
    }

    public void updateById() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        int id;
        try {
            alert.setTitle(Languages.getString("updateCommand").get());
            id = personCreatorController.getId();
            Person person1 = App.getPerson(id);
            if (person1==null) {
                alert.setContentText(Languages.getString("idNotExist").get());
                alert.showAndWait();
            }else if (person1.getUser().equals(login)) {
                person = personCreatorController.updatePerson(person1);
                if (person != null) {
                    alert.setContentText(clientManager.consoleMode(new Request(new UpdateCommand(), id, person, login, password)).getString());
                    alert.showAndWait();
                    person = null;
                }
            }else {
                alert.setContentText(Languages.getString("personError").get());
                alert.showAndWait();
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }catch (NumberFormatException ignored){}
    }

    public void removeHead() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("removeHeadCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new RemoveHeadCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
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
                alert.setTitle(Languages.getString("removeGreaterCommand").get());
                alert.setContentText(clientManager.consoleMode(new Request(new RemoveGreaterCommand(), person, login, password)).getString());
                alert.showAndWait();
                person=null;
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public void clear() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        try {
            alert.setTitle(Languages.getString("clearCommand").get());
            alert.setContentText(clientManager.consoleMode(new Request(new ClearCommand(),login,password)).getString());
            alert.showAndWait();
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
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
                alert.setTitle(Languages.getString("filterCommand").get());
                alert.setContentText(clientManager.consoleMode(new Request(new FilterLessThanEyeColorCommand(), colorE, login, password)).getString());
                alert.showAndWait();
            } catch (ConnectionException e) {
                alert.setContentText(Languages.getString("connection").get());
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
                alert.setTitle(Languages.getString("countCommand").get());
                alert.setContentText(clientManager.consoleMode(new Request(new CountGreaterThanLocationCommand(),location , login, password)).getString());
                alert.showAndWait();
            } catch (ConnectionException e) {
                alert.setContentText(Languages.getString("connection").get());
                alert.showAndWait();
                throw new ConnectionException("");
            }
        }
    }

    public void script() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(Languages.getString("scriptCommand").get());
        File file = personCreatorController.getFile();
        if (file!=null) {
            try {
                Answer answer = clientManager.scriptMode(file);
                if (answer!=null) {
                    alert.setContentText(answer.getString());
                }else alert.setContentText(Languages.getString("scriptError").get());
                alert.showAndWait();
            }catch (ConnectionException e) {
                alert.setContentText(Languages.getString("connection").get());
                alert.showAndWait();
                throw new ConnectionException("");
            }
        }
    }
}
