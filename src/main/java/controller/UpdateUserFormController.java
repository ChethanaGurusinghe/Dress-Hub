package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AlertUtils;

public class UpdateUserFormController {

    private ObservableList<User> userList;
    private User user;

    @FXML
    public Button btnUserUpdate;

    @FXML
    public Button btnUserCancel;

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

    UserService userService = new UserServiceImpl();

    @FXML
    public void initialize() {
        cmbRole.getItems().addAll("ADMIN", "EMPLOYEE");
        txtUserId.setEditable(false);
    }

    public void setUserData(User user) {

        this.user = user;
        this.userList = userList;

        txtUserId.setText(user.getUserId());
        txtFullName.setText(user.getFullName());
        txtEmail.setText(user.getEmail());
        txtPhone.setText(user.getPhoneNo());
        cmbRole.setValue(user.getRole());
    }

    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
    }

    public void btnUserUpdateOnAction(ActionEvent event) {

        try {
            if (txtFullName.getText().isEmpty() || txtEmail.getText().isEmpty() ||
                    txtPhone.getText().isEmpty() || cmbRole.getValue() == null) {
                AlertUtils.showError("All fields are required!");
                return;
            }

            boolean updated = userService.updateUser(user);

            if (updated) {
                int index = userList.indexOf(user);
                if (index >= 0) {
                    userList.set(index, user);
                }
            }

            User user = new User(
                    txtUserId.getText(),
                    txtFullName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    cmbRole.getValue()
            );

            boolean isUpdated = userService.updateUser(user);

            if (isUpdated) {
                AlertUtils.showInfo("User updated successfully!");
                ((Button) event.getSource()).getScene().getWindow().hide();
            } else {
                AlertUtils.showError("Failed to update user!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            AlertUtils.showError("Error: " + e.getMessage());
        }
    }

    public void btnUserCancelOnAction(ActionEvent event) {
        ((Button) event.getSource()).getScene().getWindow().hide();
    }
}
