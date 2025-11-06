package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class AddEmployeeFormController {

    UserService service = new UserServiceImpl();

    @FXML
    private Button btnAddEmployee;

    @FXML
    private Button btnCancelEmployee;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductId1;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductName1;

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {
        try {
            if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty() || txtEmail.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Missing Data", "Please fill all fields.");
                return;
            }

            User user = new User();
            user.setUserId(txtUserId.getText());
            user.setName(txtName.getText());
            user.setPhone(txtPhone.getText());
            user.setEmail(txtEmail.getText());

            boolean added = service.addUser(user);
            if (added) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee added successfully.");
                closeStage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Could not add employee.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnCancelEmployeeOnAction(ActionEvent event) {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) btnCancelEmployee.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
