package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.AlertUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserFormController implements Initializable {

    public AnchorPane root1;
    public ImageView logoImage;
    public Label lblTittle;
    public AnchorPane root2;
    public Label lblSubTittle;
    public Rectangle rectangle;
    public Label lblUserId;
    public Label lblFullName;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblRole;
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

                if (userList != null) {
                    userList.add(user);
                }

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

        Platform.runLater(() -> {
            centerContent(); // first call

            root1.widthProperty().addListener((obs, oldV, newV) -> centerContent());
            root1.heightProperty().addListener((obs, oldV, newV) -> centerContent());
        });

        cmbRole.getItems().addAll("ADMIN", "EMPLOYEE");
        txtUserId.setText(userService.generateNextUserId());
        txtUserId.setEditable(false);
    }

    private void centerContent() {

        double paneW = root1.getWidth();
        double paneH = root1.getHeight();

        if (paneW < 200 || paneH < 200) return;  // avoid invalid sizes during first layout pass

        // ----------- POSITION LOGO -------------
        logoImage.setLayoutX((paneW - logoImage.getFitWidth()) / 2);
        logoImage.setLayoutY(20);

        // ----------- TITLE -----------
        lblTittle.setLayoutX((paneW / 2) - 100);  // assume approx width
        lblTittle.setLayoutY(logoImage.getLayoutY() + 80);

        // ----------- SUB TITLE -----------
        lblSubTittle.setLayoutX((paneW / 2) - 80);
        lblSubTittle.setLayoutY(lblTittle.getLayoutY() + 50);

        // ----------- MAIN CARD (rectangle) ----------
        double rectW = paneW * 0.75;
        double rectH = paneH * 0.63;

        rectangle.setWidth(rectW);
        rectangle.setHeight(rectH);

        rectangle.setLayoutX((paneW - rectW) / 2);
        rectangle.setLayoutY((paneH - rectH) / 2);

        // ----------- LAYOUT BASE POSITIONS -----------
        double leftX = rectangle.getLayoutX() + 110;      // left column X
        double rightX = rectangle.getLayoutX() + rectW - 360; // right column X

        double row1 = rectangle.getLayoutY() + 50;
        double row2 = row1 + 70;
        double row3 = row2 + 70;
        double row4 = row3 + 70;

        // ----------- ROW 1 -----------
        lblUserId.setLayoutX(leftX);
        txtUserId.setLayoutX(leftX + 150);

        lblFullName.setLayoutX(rightX);
        txtFullName.setLayoutX(rightX + 130);

        lblUserId.setLayoutY(row1);
        txtUserId.setLayoutY(row1);

        lblFullName.setLayoutY(row1);
        txtFullName.setLayoutY(row1);

        // ----------- ROW 2 -----------
        lblPhone.setLayoutX(leftX);
        txtPhone.setLayoutX(leftX + 150);

        lblEmail.setLayoutX(rightX);
        txtEmail.setLayoutX(rightX + 130);

        lblPhone.setLayoutY(row2);
        txtPhone.setLayoutY(row2);

        lblEmail.setLayoutY(row2);
        txtEmail.setLayoutY(row2);

        // ----------- ROW 3 -----------
        lblRole.setLayoutX(leftX);
        cmbRole.setLayoutX(leftX + 150);

        lblRole.setLayoutY(row3);
        cmbRole.setLayoutY(row3);

        // ----------- BUTTONS -----------
        double btnY = rectangle.getLayoutY() + rectH - 70;

        btnAddUser.setLayoutX((paneW / 2) - 150);
        btnAddUser.setLayoutY(btnY);

        btnCancelUser.setLayoutX((paneW / 2) + 20);
        btnCancelUser.setLayoutY(btnY);
    }

}
