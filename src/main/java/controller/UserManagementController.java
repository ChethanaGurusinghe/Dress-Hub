package controller;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserManagementController {

    public Button btnUserManagement;
    public Button btnAddUser;
    public TableView tblUser;
    public TableColumn colRole;
    UserService service = new UserServiceImpl();
    ObservableList<User> employeeList = FXCollections.observableArrayList();

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

    }

    Stage edituserStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        try {
            edituserStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateUser.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        edituserStage.show();
    }


    @FXML
    void btnLogOutOnAction(ActionEvent event) {

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
}
