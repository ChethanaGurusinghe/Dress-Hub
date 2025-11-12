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
import model.dto.OrderDetail;
import model.dto.Product;
import service.OrderService;
import service.impl.OrderServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class OrderPlacementFormController {

    @FXML
    public Button btnInvoicePrint;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnAddToCart;

    @FXML
    public Button btnLogOut;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtOrderId;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblProductName;

    @FXML
    private TableView<OrderDetail> tblCart;

    @FXML
    private TableColumn<OrderDetail, String> colItemCode;

    @FXML
    private TableColumn<OrderDetail, String> colName;

    @FXML
    private TableColumn<OrderDetail, Double> colUnitPrice;

    @FXML
    private TableColumn<OrderDetail, Integer> colQuantity;

    @FXML
    private TableColumn<OrderDetail, Double> colTotal;

    private final ObservableList<OrderDetail> cartList = FXCollections.observableArrayList();
    private final OrderService orderService = new OrderServiceImpl();

    @FXML
    public void initialize() {

        btnAddToCart.setDisable(true); // disabled

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

        // Table column setup
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("orderQty"));

        colName.setCellValueFactory(cellData -> {
            try {
                Product p = orderService.getProductById(cellData.getValue().getProductId());
                return new SimpleStringProperty(p != null ? p.getDescription() : "");
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleStringProperty("");
            }
        });

        colUnitPrice.setCellValueFactory(cellData -> {
            try {
                Product p = orderService.getProductById(cellData.getValue().getProductId());
                return new SimpleDoubleProperty(p != null ? p.getUnitPrice() : 0.0).asObject();
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleDoubleProperty(0.0).asObject();
            }
        });

        colTotal.setCellValueFactory(cellData -> {
            try {
                Product p = orderService.getProductById(cellData.getValue().getProductId());
                double total = (p != null ? p.getUnitPrice() : 0.0) * cellData.getValue().getOrderQty();
                return new SimpleDoubleProperty(total).asObject();
            } catch (SQLException e) {
                e.printStackTrace();
                return new SimpleDoubleProperty(0.0).asObject();
            }
        });
    }

    private void fetchProduct() {
        String productId = txtProductId.getText().trim();
        if (productId.isEmpty()) return;

        try {
            Product product = orderService.getProductById(productId);
            if (product != null) {
                lblProductName.setText(product.getDescription());
                lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
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

            // Update labels
            lblProductName.setText(product.getDescription());
            lblUnitPrice.setText(String.format("%.2f", product.getUnitPrice()));

            // Add to cart
            cartList.add(new OrderDetail(productId, quantity));
            updateNetTotal();

            // Clear input fields
            txtProductId.clear();
            txtQuantity.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error occurred!");
        }
    }

//    @FXML
//    private void btnPlaceOrderOnAction(ActionEvent event) {
//        if (cartList.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "Cart is empty");
//            return;
//        }
//
//        String orderId = txtOrderId.getText().trim();
//        if (orderId.isEmpty()) {
//            showAlert(Alert.AlertType.WARNING, "OrderID is empty");
//            return;
//        }
//
//        try {
//            orderService.placeOrder(orderId, cartList);
//            showAlert(Alert.AlertType.INFORMATION, "Order placed successfully!");
//
//            // Clear cart and generate new OrderID
//            cartList.clear();
//            updateNetTotal();
//            txtOrderId.setText(orderService.generateOrderId());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            showAlert(Alert.AlertType.ERROR, "Failed to place order. Check database constraints.");
//        }
//    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        // Clear cart and reset net total
        cartList.clear();
        updateNetTotal();
        lblProductName.setText("");
        lblUnitPrice.setText("");
        txtProductId.clear();
        txtQuantity.clear();
    }

    private void updateNetTotal() {
        double total = 0.0;
        for (OrderDetail od : cartList) {
            try {
                Product p = orderService.getProductById(od.getProductId());
                if (p != null) {
                    total += p.getUnitPrice() * od.getOrderQty();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
}
