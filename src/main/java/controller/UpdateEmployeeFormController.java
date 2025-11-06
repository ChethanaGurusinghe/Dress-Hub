package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class UpdateEmployeeFormController {

    UserService service = new UserServiceImpl();

    @FXML
    private Button btnEmpCancel;

    @FXML
    private Button btnEmpUpdate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    void btnEmpCancelOnAction(ActionEvent event) {
        closeStage();
    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        try {
            User user = new User();
            user.setUserId(txtUserId.getText());
            user.setName(txtName.getText());
            user.setPhone(txtPhone.getText());
            user.setEmail(txtEmail.getText());
            user.setRole("employee");

            boolean updated = service.updateUser(user);
            if (updated) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Employee updated successfully.");
                closeStage();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Could not update employee.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void setEmployeeData(User selected) {

        txtUserId.setText(selected.getUserId());
        txtName.setText(selected.getName());
        txtPhone.setText(selected.getPhone());
        txtEmail.setText(selected.getEmail());
    }

    private void closeStage() {
        Stage stage = (Stage) btnEmpCancel.getScene().getWindow();
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
