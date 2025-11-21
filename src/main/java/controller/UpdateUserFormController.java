package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AlertUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateUserFormController implements Initializable {

    public AnchorPane rootPane;
    public ImageView logoImage;
    public Label lblTitle;
    public Rectangle rectangle1;
    public Label lblUserId;
    public Label lblFullName;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblSubTitle;
    public Label lblRole;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMaximized = false;

    private double prevX, prevY, prevWidth, prevHeight;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        rootPane.setOnMousePressed(event -> {
            if (!isMaximized) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        rootPane.setOnMouseDragged(event -> {
            if (!isMaximized) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        // ----------- Double-click Maximize/Restore -----------
        rootPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                toggleMaximize();
            }
        });

        cmbRole.getItems().addAll("ADMIN", "EMPLOYEE");
        txtUserId.setEditable(false);
    }

    private void toggleMaximize() {
        Stage stage = (Stage) rootPane.getScene().getWindow();

        if (!isMaximized) {
            // Save old bounds
            prevX = stage.getX();
            prevY = stage.getY();
            prevWidth = stage.getWidth();
            prevHeight = stage.getHeight();

            // Maximize
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());

            isMaximized = true;

        } else {
            // Restore
            stage.setX(prevX);
            stage.setY(prevY);
            stage.setWidth(prevWidth);
            stage.setHeight(prevHeight);

            isMaximized = false;
        }
    }
}
