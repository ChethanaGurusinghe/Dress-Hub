package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

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
    private BarChart<String, Number> salesChart;
    private EventObject event;

    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {

    }

    private void loadDashboardData() {
        try (Connection connection = DBConnection.getInstance().getConnection()) {

            // Total Sales
            String sqlSales = """
            SELECT SUM(od.orderQty * p.unit_price) AS total_sales FROM orderdetail od JOIN product p ON od.product_id = p.product_id """;
            PreparedStatement pstSales = connection.prepareStatement(sqlSales);
            ResultSet rsSales = pstSales.executeQuery();
            double totalSales = rsSales.next() ? rsSales.getDouble("total_sales") : 0;

            // Total Orders
            String sqlOrders = "SELECT COUNT(*) AS total_orders FROM orders";
            PreparedStatement pstOrders = connection.prepareStatement(sqlOrders);
            ResultSet rsOrders = pstOrders.executeQuery();
            int totalOrders = rsOrders.next() ? rsOrders.getInt("total_orders") : 0;

            // Total Products
            String sqlProducts = "SELECT COUNT(*) AS total_products FROM product";
            PreparedStatement pstProducts = connection.prepareStatement(sqlProducts);
            ResultSet rsProducts = pstProducts.executeQuery();
            int totalProducts = rsProducts.next() ? rsProducts.getInt("total_products") : 0;

            // Low Stock
            String sqlLowStock = "SELECT COUNT(*) AS low_stock FROM product WHERE quantity < 10";
            PreparedStatement pstLowStock = connection.prepareStatement(sqlLowStock);
            ResultSet rsLowStock = pstLowStock.executeQuery();
            int lowStock = rsLowStock.next() ? rsLowStock.getInt("low_stock") : 0;

            // Update your dashboard cards
            lblTotalSales.setText(String.format("Rs. %.2f", totalSales));
            lblTotalOrders.setText(String.valueOf(totalOrders));
            lblTotalProducts.setText(String.valueOf(totalProducts));
            lblLowStock.setText(String.valueOf(lowStock));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    private void loadSalesChart() {
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Sales by Category");
//
//        try (Connection con = DBConnection.getInstance().getConnection()) {
//            String sql = "SELECT category, SUM(quantity) AS total FROM orders GROUP BY category";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//
//            while (rs.next()) {
//                series.getData().add(new XYChart.Data<>(rs.getString("category"), rs.getInt("total")));
//            }
//
//            salesChart.getData().clear();
//            salesChart.getData().add(series);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDashboardData();
    }

    Stage productStage = new Stage();
    public void btnProdutManagementOnAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProductManagement.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Product Management");
            stage.setScene(scene);
            stage.show();

            // Close the current dashboard window
            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
