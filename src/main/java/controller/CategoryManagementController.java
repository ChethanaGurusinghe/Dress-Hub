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

public class CategoryManagementController {

    @FXML
    private Button btnAddCategory;

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
    private TableColumn<?, ?> colCategoryId;

    @FXML
    private TableColumn<?, ?> colCategoryName;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TextField searchTxtFeild;

    @FXML
    private TableView<?> tblCategory;

    Stage addCategoryStage = new Stage();
    @FXML
    void btnAddCategoryOnAction(ActionEvent event) {
        try {
            addCategoryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddCategoryForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addCategoryStage.show();
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

    @FXML
    void btnCategoryManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    Stage editCategoryStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        try {
            editCategoryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateCategoryForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        editCategoryStage.show();
    }

    Stage employeeStage = new Stage();
    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {
        try {
            employeeStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/EmployeeManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        employeeStage.show();
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

}
