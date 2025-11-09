package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AlertUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {

    private ObservableList<User> userList;
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

    public void setUserList(ObservableList<User> userList) {
        this.userList = userList;
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

            user.setUserName(userService.generateUsername(user.getFullName()));
            user.setPassword(userService.generatePassword(user.getFullName()));

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbRole.getItems().addAll("ADMIN", "EMPLOYEE");
        txtUserId.setText(userService.generateNextUserId());
        txtUserId.setEditable(false);
    }
}
