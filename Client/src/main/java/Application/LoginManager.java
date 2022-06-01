package Application;

import Client.ClientManager;
import exceptions.ConnectionException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginManager {
    private String userName;
    private String password;
    private Alert alert;
    private ClientManager clientManager;
    private CommandsManager commandsManager;

    public LoginManager (ClientManager clientManager, CommandsManager commandsManager) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        this.clientManager=clientManager;
        this.commandsManager=commandsManager;
    }

    public boolean signUp (String userName, String password) {
        alert.setTitle("Sign up");
        try {
            if (!clientManager.signUp(userName, password)) {
                alert.setContentText("Sign up success");
                alert.showAndWait();
                return true;
            } else {
                alert.setContentText("User already exists");
                alert.showAndWait();
                return false;
            }
        } catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public boolean signIn(String userName, String password) {
        alert.setTitle("Sign in");
        try {
            if (!clientManager.signIn(userName,password)) {
                this.userName = userName;
                alert.setTitle("Sign in");
                alert.setContentText("Sign in success");
                alert.showAndWait();
                clientManager.setUser(userName, password);
                commandsManager.setUser(userName, password);
                return true;
            } else {
                alert.setTitle("Sign in");
                alert.setContentText("Wrong login or password");
                alert.showAndWait();
                return false;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Try later");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public String getUserName() {return userName;}
}