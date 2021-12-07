package dam.jlr.fichajes0;

import dam.jlr.fichajes0.HelloController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {
    private final String user="root";
    private final String password="root";
    private boolean isLoggedIn = false;
    @javafx.fxml.FXML
    private TextField usertextfield;
    @javafx.fxml.FXML
    private PasswordField passwordtextfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void tryLogin(ActionEvent actionEvent) {
        if(usertextfield.getText().equalsIgnoreCase(user) && passwordtextfield.getText().equalsIgnoreCase(password)){
            isLoggedIn=true;
        }
        System.out.println(isLoggedIn);
    }

    public boolean isLoggedIn() {
        System.out.println(isLoggedIn);
        return isLoggedIn;
    }
}





