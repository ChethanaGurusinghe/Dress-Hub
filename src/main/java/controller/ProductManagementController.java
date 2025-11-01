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

    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {

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

    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {

    }

}
