package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeManagementController {

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnEmployeeManagement;

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

    @FXML
    private TableView<?> tblEmployee;

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

    Stage adminStage = new Stage();
    @FXML
    void btnAdminManagementOnAction(ActionEvent event) {
        try {
            adminStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminStage.show();
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

    Stage editEmployeeStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        try {
            editEmployeeStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateEmployee.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editEmployeeStage.show();
    }

    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {

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

    Stage addEmployeeStage = new Stage();
    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {

        try {
            addEmployeeStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddEmployeeForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addEmployeeStage.show();
    }

}
