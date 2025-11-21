package controller;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserManagementController implements Initializable {

    public Button btnUserManagement;
    public Button btnAddUser;
    public TableView tblUser;
    public TableColumn colRole;
    public AnchorPane rootPane;
    public Rectangle rec1;
    public ImageView logoImage;
    public Label lblTitle;
    public Rectangle rec2;
    public Text lblHeader;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMaximized = false;

    private double prevX, prevY, prevWidth, prevHeight;

    UserService service = new UserServiceImpl();
    ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnSupplierManagement;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TextField searchTxtFeild;


    Stage adminDashboardStage = new Stage();
    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {
        try {
            adminDashboardStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminDashboardStage.show();
    }


    Stage categorySatge = new Stage();
    @FXML
    void btnCategoryManagementOnAction(ActionEvent event) {
        try {
            categorySatge.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CategoryManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categorySatge.show();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        User selectedUser = (User) tblUser.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.show();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete user " + selectedUser.getFullName() + " (" + selectedUser.getUserId() + ") ?");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirm.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == yes) {
            try {
                boolean deleted = service.deleteUser(selectedUser.getUserId());
                if (deleted) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deleted");
                    alert.setHeaderText(null);
                    alert.setContentText("User deleted successfully.");
                    alert.show();

                    // Remove from ObservableList so TableView updates immediately
                    userList.remove(selectedUser);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Delete Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("Could not delete user.");
                    alert.show();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error deleting user: " + e.getMessage());
                alert.show();
                e.printStackTrace();
            }
        }
    }

    Stage edituserStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        User selectedUser = (User) tblUser.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a user to edit!").show();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateUser.fxml"));
            Parent root = loader.load();

            // Get controller of UpdateUser form
            UpdateUserFormController controller = loader.getController();
            controller.setUserData(selectedUser);
            controller.setUserList(userList);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
    }

    Stage productStage = new Stage();
    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {
        try {
            productStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProductManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productStage.show();
    }

    Stage supplierStage = new Stage();
    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SupplierManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        supplierStage.show();
    }

    public void btnUserManagementOnAction(ActionEvent actionEvent) {
        //current page
    }

    Stage addUserStage = new Stage();
    public void btnAddUserOnAction(ActionEvent actionEvent) {
        try {
            addUserStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddUserForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addUserStage.show();
    }

    private void loadUserTable() {
        userList.clear();
        List<User> users = service.getAllUsers();

        if (users != null) {
            userList.addAll(users);
        }

        tblUser.setItems(userList);
    }

    public void searchTxtFeildOnAction(ActionEvent event) {
        String keyword = searchTxtFeild.getText().trim();

        if (keyword.isEmpty()) {
            loadUserTable();
            return;
        }

        List<User> results = service.searchUsers(keyword);
        userList.clear();

        if (!results.isEmpty()) {
            userList.addAll(results);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "No users found!").show();
        }

        tblUser.setItems(userList);
    }

    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    // Load LoginForm.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    stage.setTitle("Login");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // If "No" is selected, do nothing
        });
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

        // ----------------- Double Click Maximize -----------------
        rootPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                toggleMaximize();
            }
        });

        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        loadUserTable();
        tblUser.refresh();

        searchTxtFeild.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadUserTable();
            } else {
                List<User> results = service.searchUsers(newValue);
                userList.clear();
                userList.addAll(results);
                tblUser.setItems(userList);
            }
        });
    }

    private void toggleMaximize() {
        Stage stage = (Stage) rootPane.getScene().getWindow();

        if (!isMaximized) {
            // Save previous window size
            prevX = stage.getX();
            prevY = stage.getY();
            prevWidth = stage.getWidth();
            prevHeight = stage.getHeight();

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            stage.setX(screenBounds.getMinX());
            stage.setY(screenBounds.getMinY());
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());

            isMaximized = true;

        } else {
            // Restore window size
            stage.setX(prevX);
            stage.setY(prevY);
            stage.setWidth(prevWidth);
            stage.setHeight(prevHeight);

            isMaximized = false;
        }
    }

}
