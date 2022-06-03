package Application;

import Client.ClientManager;
import Language.Languages;
import exceptions.ConnectionException;
import javafx.scene.control.Alert;

public class LoginManager {
    private String userName;
    private String password;
    private Alert alert;
    private final ClientManager clientManager;
    private final CommandsManager commandsManager;

    public LoginManager (ClientManager clientManager, CommandsManager commandsManager) {
        this.clientManager=clientManager;
        this.commandsManager=commandsManager;
    }

    public boolean signUp (String userName, String password) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(Languages.getString("registerTitle").get());
        try {
            if (!clientManager.signUp(userName, password)) {
                alert.setContentText(Languages.getString("registerOk").get());
                alert.showAndWait();
                return true;
            } else {
                alert.setContentText(Languages.getString("registerError").get());
                alert.showAndWait();
                return false;
            }
        } catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public boolean signIn(String userName, String password) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle(Languages.getString("loginTitle").get());
        try {
            if (!clientManager.signIn(userName,password)) {
                this.userName = userName;
                alert.setContentText(Languages.getString("loginOk").get());
                alert.showAndWait();
                clientManager.setUser(userName, password);
                commandsManager.setUser(userName, password);
                return true;
            } else {
                alert.setContentText(Languages.getString("loginError").get());
                alert.showAndWait();
                return false;
            }
        }catch (ConnectionException e) {
            alert.setContentText(Languages.getString("connection").get());
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public String getUserName() {return userName;}
}