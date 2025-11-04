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

public class ProductManagementController {

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
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQtyInInventory;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TextField searchTxtFeild;

    @FXML
    private TableView<?> tblProducts;

    Stage addproductstage = new Stage();
    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            addproductstage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addproductstage.show();
    }

    Stage dashboardStage = new Stage();
    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {
        try {
            dashboardStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dashboardStage.show();
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

    Stage updateproductStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        try {
            updateproductStage.setScene(new Scene( FXMLLoader.load(getClass().getResource("/view/UpdateProductForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateproductStage.show();
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

    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {

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
