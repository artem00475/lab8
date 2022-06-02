package Application;

import Client.ClientManager;
import exceptions.ConnectionException;
import javafx.scene.control.Alert;

public class LoginManager {
    private String userName;
    private String password;
    private final Alert alert;
    private final ClientManager clientManager;
    private final CommandsManager commandsManager;

    public LoginManager (ClientManager clientManager, CommandsManager commandsManager) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        this.clientManager=clientManager;
        this.commandsManager=commandsManager;
    }

    public boolean signUp (String userName, String password) {
        alert.setTitle("Регистрация");
        try {
            if (!clientManager.signUp(userName, password)) {
                alert.setContentText("Регистрация прошла успешно");
                alert.showAndWait();
                return true;
            } else {
                alert.setContentText("Пользоваетль уже существует");
                alert.showAndWait();
                return false;
            }
        } catch (ConnectionException e) {
            alert.setContentText("Попробуйте позже");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public boolean signIn(String userName, String password) {
        alert.setTitle("Вход");
        try {
            if (!clientManager.signIn(userName,password)) {
                this.userName = userName;
                alert.setContentText("Вход выполнен");
                alert.showAndWait();
                clientManager.setUser(userName, password);
                commandsManager.setUser(userName, password);
                return true;
            } else {
                alert.setContentText("Неверный логин или пароль, или пользователь уже авторизован");
                alert.showAndWait();
                return false;
            }
        }catch (ConnectionException e) {
            alert.setContentText("Попробуйте позже");
            alert.showAndWait();
            throw new ConnectionException("");
        }
    }

    public String getUserName() {return userName;}
}