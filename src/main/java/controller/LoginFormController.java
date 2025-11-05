package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class LoginFormController {

    @FXML
    private Button btnAdminLogIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    UserService userService = new UserServiceImpl();

    Stage adminstage = new Stage();
    @FXML
    void btnAdminLogInOnAction(ActionEvent event) {
//        try {
//            adminstage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        adminstage.show();


        String username = txtUserName.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter username and password!").show();
            return;
        }

        User user = userService.checkLogin(username, password);

        if (user != null) {
            try {
                Stage stage = (Stage) txtUserName.getScene().getWindow();
                if (user.getRole().equalsIgnoreCase("admin")) {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
                    stage.setTitle("Admin Dashboard - DressHub");
                } else {
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/OrderPlacementForm.fxml"))));
                    stage.setTitle("Employee Dashboard - DressHub");
                }
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
        }

    }

}
