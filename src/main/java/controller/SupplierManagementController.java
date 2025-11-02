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

public class SupplierManagementController {

    public TableView tblSupplier;
    @FXML
    private Button btnAddSupplier;

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
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNotes;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TextField searchTxtFeild;

    Stage addSupplierStage = new Stage();
    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        try {
            addSupplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddSupplierForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addSupplierStage.show();
    }

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

    @FXML
    void btnAdminManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnCategoryManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    Stage editSupplierStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        try {
            editSupplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateSupplierForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editSupplierStage.show();
    }

    Stage employeeManagementStage = new Stage();
    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {

        try {
            employeeManagementStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeeManagementStage.show();
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    Stage productManagementstage = new Stage();
    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {

        try {
            productManagementstage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProductManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productManagementstage.show();
    }

    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {

    }

}
