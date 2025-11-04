package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeLoginFormController {

    @FXML
    private Button btnEmployeeLogIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    Stage loginStage = new Stage();
    @FXML
    void btnEmployeeLogInOnAction(ActionEvent event) {
        try {
            loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderPlacementForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loginStage.show();
    }

}
