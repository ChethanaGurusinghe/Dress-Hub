package controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.dto.Bill;
import model.dto.OrderDetail;
import model.dto.Product;
import service.OrderService;
import service.impl.OrderServiceImpl;
import util.InvoiceGenerator;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderPlacementFormController {

    @FXML
    public Button btnInvoicePrint, btnCancel, btnAddToCart, btnLogOut;

    @FXML
    private TextField txtProductId, txtQuantity, txtOrderId;

    @FXML
    private Label lblNetTotal, lblUnitPrice, lblProductName;

    @FXML
    private TableView<OrderDetail> tblCart;

    @FXML
    private TableColumn<OrderDetail, String> colItemCode, colName;

    @FXML
    private TableColumn<OrderDetail, Double> colUnitPrice, colTotal;

    @FXML
    private TableColumn<OrderDetail, Integer> colQuantity;

    private final ObservableList<OrderDetail> cartList = FXCollections.observableArrayList();
    private final OrderService orderService = new OrderServiceImpl();

    @FXML
    public void initialize() {

        btnAddToCart.setDisable(true);

        txtProductId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fetchProduct();
            }
        });

        // Auto-generate OrderID
        try {
            String orderId = orderService.generateOrderId();
            txtOrderId.setText(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Bind TableView to cartList
        tblCart.setItems(cartList);

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getUnitPrice()).asObject()
        );
        colTotal.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getTotal()).asObject()
        );
    }

    private void fetchProduct() {
        String productId = txtProductId.getText().trim();
        if (productId.isEmpty()) return;

        try {
            Product product = orderService.getProductById(productId);
            if (product != null) {
                lblProductName.setText(product.getDescription());
                lblUnitPrice.setText(String.format("%.2f", product.getUnitPrice()));
                btnAddToCart.setDisable(false);
            } else {
                lblProductName.setText("Invalid Product ID");
                lblUnitPrice.setText("0.00");
                btnAddToCart.setDisable(true);
                txtProductId.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnAddToCartOnAction(ActionEvent event) {
        String productId = txtProductId.getText().trim();
        String qtyText = txtQuantity.getText().trim();

        if (productId.isEmpty() || qtyText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter Product ID and Quantity");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyText);
            if (quantity <= 0) {
                showAlert(Alert.AlertType.WARNING, "Quantity must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Invalid Quantity");
            return;
        }

        try {
            Product product = orderService.getProductById(productId);
            if (product == null) {
                showAlert(Alert.AlertType.ERROR, "Product not found!");
                return;
            }

            // Add to cart
            cartList.add(new OrderDetail(productId, product.getDescription(), product.getUnitPrice(), quantity));
            updateNetTotal();

            // Clear input fields
            txtProductId.clear();
            txtQuantity.clear();
            lblProductName.setText("");
            lblUnitPrice.setText("");
            btnAddToCart.setDisable(true);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error occurred!");
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        cartList.clear();
        updateNetTotal();
        txtProductId.clear();
        txtQuantity.clear();
        lblProductName.setText("");
        lblUnitPrice.setText("");
        btnAddToCart.setDisable(true);
    }

    private void updateNetTotal() {
        double total = cartList.stream().mapToDouble(OrderDetail::getTotal).sum();
        lblNetTotal.setText(String.format("%.2f", total));
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    public void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
    }

    @FXML
    public void btnInvoicePrintOnAction(ActionEvent event) {
        handlePrintInvoice(event);
    }

    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
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
        });
    }

    private void handlePrintInvoice(ActionEvent event) {
        try {
            String orderId = txtOrderId.getText().trim();

            // Check if order exists first
            if (!orderService.isOrderExists(orderId)) {
                showAlert(Alert.AlertType.ERROR, "Order ID does not exist in orders table!");
                return;
            }

            BillController billController = new BillController();

            boolean saved = billController.createBill(orderId);

            if (saved) {
                double totalAmount = cartList.stream().mapToDouble(OrderDetail::getTotal).sum();
                InvoiceGenerator.generateInvoice(billController.generateInvoiceNo(), orderId, cartList, totalAmount);
                new Alert(Alert.AlertType.INFORMATION, "Invoice printed successfully!").show();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to create bill in DB");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error generating invoice!");
        }
    }


}
