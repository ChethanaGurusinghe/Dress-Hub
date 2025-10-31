package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class RoleSelectionController {

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnEmployee;

    Stage adminloginstage = new Stage();
    @FXML
    void btnAdminOnAction(ActionEvent event) {
        try {
            adminloginstage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminLoginForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminloginstage.show();
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) {

    }

}
