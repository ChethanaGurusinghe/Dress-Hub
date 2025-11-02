package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDashboardController {

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnSupplierManagement;

    @FXML
    private Label lblLowStock;

    @FXML
    private Label lblTotalOrders;

    @FXML
    private Label lblTotalProducts;

    @FXML
    private Label lblTotalSales;

    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {

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
