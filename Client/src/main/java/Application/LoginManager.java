package Application;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginManager {
    private String userName;
    private String password;
    private TextField user;
    private PasswordField passwordField;
    public LoginManager (TextField user, PasswordField passwordField) {
        this.passwordField=passwordField;
        this.user=user;
    }

    public void signUp () {
        System.out.println(1);
    }

    public void signIn() {
        System.out.println(2);
    }
}