package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AlertUtils;

public class AddUserFormController {

    UserService userService = new UserServiceImpl();

    @FXML
    private TextField txtUserId;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private ComboBox<String> cmbRole;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnCancelUser;

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("ADMIN", "EMPLOYEE");
        txtUserId.setText(userService.generateNextUserId());
        txtUserId.setEditable(false);
    }

    @FXML
    void btnAddUserOnAction(ActionEvent event) {
        try {
            if (txtFullName.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                    txtPhone.getText().isEmpty() || cmbRole.getValue() == null) {
                AlertUtils.showError("All fields are required!");
                return;
            }

            User user = new User(
                    txtUserId.getText(),
                    txtFullName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),

                    cmbRole.getValue()
            );

            boolean isSaved = userService.addUser(user);

            if (isSaved) {
                AlertUtils.showInfo("User added successfully!");
                ((Button) event.getSource()).getScene().getWindow().hide();
            } else {
                AlertUtils.showError("Failed to add user!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Error: " + e.getMessage());
        }
    }

    @FXML
    void btnCancelUserOnAction(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
