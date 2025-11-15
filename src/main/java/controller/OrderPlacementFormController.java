package controller;

import javafx.beans.property.SimpleDoubleProperty;
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

        // ENTER to fetch product
        txtProductId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                fetchProduct();
            }
        });

        // Auto-generate order ID
        try {
            txtOrderId.setText(orderService.generateOrderId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Bind table columns
        tblCart.setItems(cartList);
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        colName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colUnitPrice.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getUnitPrice()).asObject()
        );
        colTotal.setCellValueFactory(cell ->
                new SimpleDoubleProperty(cell.getValue().getTotal()).asObject()
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
        String orderId = txtOrderId.getText().trim();

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
            cartList.add(new OrderDetail(
                    orderId,
                    productId,
                    product.getDescription(),
                    product.getUnitPrice(),
                    quantity
            ));

            updateNetTotal();

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
        alert.setContentText(msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // Logout
    @FXML
    public void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
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

    // Print Invoice
    @FXML
    public void btnInvoicePrintOnAction(ActionEvent event) {
        handlePrintInvoice(event);
    }


    private void handlePrintInvoice(ActionEvent event) {

        try {
            String orderId = txtOrderId.getText().trim();
            double totalAmount = cartList.stream().mapToDouble(OrderDetail::getTotal).sum();

            // Save order once
            if (!orderService.isOrderExists(orderId)) {

                boolean savedOrder = orderService.saveOrder(orderId, totalAmount);

                if (!savedOrder) {
                    showAlert(Alert.AlertType.ERROR, "Failed to save order!");
                    return;
                }

                // Save each order detail
                for (OrderDetail od : cartList) {
                    orderService.saveOrderDetail(orderId, od.getProductId(), od.getOrderQty());
                }
            }

            for (OrderDetail od : cartList) {
                orderService.reduceProductStock(od.getProductId(), od.getOrderQty());
            }

            // Create bill → save in DB
            BillController billController = new BillController();
            boolean billSaved = billController.createBill(orderId);

            if (!billSaved) {
                showAlert(Alert.AlertType.ERROR, "Failed to create bill!");
                return;
            }

            // Generate invoice PDF
            String invoiceNo = billController.generateInvoiceNo();
            InvoiceGenerator.generateInvoice(invoiceNo, orderId, cartList, totalAmount);

            new Alert(Alert.AlertType.INFORMATION, "Invoice printed successfully!").show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error generating invoice!");
        }
    }
}
