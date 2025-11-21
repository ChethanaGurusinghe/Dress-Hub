package controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    public ImageView logoImage;
    public Label lblTitle;
    public Line separatorLine;
    public Label lblWelcome;
    public Label lblSubText;
    public Label lblUsername;
    public Label lblPassword;
    @FXML
    private AnchorPane rootPane;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            // Centering logic runs AFTER FXML loads
            centerContent();

            // Listen for window resize
            rootPane.widthProperty().addListener((obs, oldV, newV) -> centerContent());
            rootPane.heightProperty().addListener((obs, oldV, newV) -> centerContent());
        });
    }

    private void centerContent() {

        double paneWidth = rootPane.getWidth();
        double paneHeight = rootPane.getHeight();

        if (paneWidth == 0 || paneHeight == 0) return;

        double centerX = paneWidth / 2;
        double centerY = paneHeight / 2;

        double rightGroupCenterY = 350;
        double rightYOffset = centerY - rightGroupCenterY;
        double rightCenter = paneWidth * 0.70;

        lblWelcome.setLayoutY(100 + rightYOffset);
        lblSubText.setLayoutY(165 + rightYOffset);

        lblUsername.setLayoutY(246 + rightYOffset);
        txtUserName.setLayoutY(278 + rightYOffset);

        lblPassword.setLayoutY(355 + rightYOffset);
        txtPassword.setLayoutY(393 + rightYOffset);

        btnAdminLogIn.setLayoutY(480 + rightYOffset);

        lblWelcome.setLayoutX(rightCenter - 100);
        lblSubText.setLayoutX(rightCenter - 100);

        lblUsername.setLayoutX(rightCenter - 120);
        txtUserName.setLayoutX(rightCenter - 120);

        lblPassword.setLayoutX(rightCenter - 120);
        txtPassword.setLayoutX(rightCenter - 120);

        btnAdminLogIn.setLayoutX(rightCenter - 40);
        separatorLine.setLayoutX(centerX + 75);

        double leftCenter = centerX - 200;
        double leftGroupCenterY = 330;
        double leftYOffset = centerY - leftGroupCenterY;

        // Logo
        logoImage.setLayoutY(200 + leftYOffset);
        logoImage.setLayoutX(leftCenter - 400);

        // Title (DressHub)
        lblTitle.setLayoutY(275 + leftYOffset);
        lblTitle.setLayoutX(leftCenter - 200);
    }

}
