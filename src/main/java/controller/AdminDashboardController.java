package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    public Button btnUserManagement;
    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnCategoryManagement;


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

    @FXML
    private EventObject event;

    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {
        //current page
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
    void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
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
        loadSalesChart();
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

    Stage userStage = new Stage();
    public void btnUserManagementOnAction(ActionEvent actionEvent) {
        try {
            userStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userStage.show();
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

    private void loadSalesChart() {
        salesChart.getData().clear(); // Clear data

        try (Connection connection = DBConnection.getInstance().getConnection()) {

            String sql = """
                    SELECT c.name AS category,\s
                                   SUM(od.orderQty * p.unit_price) AS total_sales
                                   FROM orderdetail od
                                   JOIN product p ON od.product_id = p.product_id
                                   JOIN category c ON p.category_id = c.category_id
                                   GROUP BY c.name
                                   ORDER BY\s
                                       CASE c.name
                                           WHEN 'Ladies' THEN 1
                                           WHEN 'Gents' THEN 2
                                           WHEN 'Kids' THEN 3
                                       END
                """;

            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            // preparing a series for the bar chart
            javafx.scene.chart.XYChart.Series<String, Number> series = new javafx.scene.chart.XYChart.Series<>();
            series.setName("Sales by Category");

            while (rs.next()) {
                String category = rs.getString("category");
                double sales = rs.getDouble("total_sales");

                series.getData().add(new javafx.scene.chart.XYChart.Data<>(category, sales));
            }

            salesChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
