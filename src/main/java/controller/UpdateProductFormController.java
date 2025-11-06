package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dto.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

public class UpdateProductFormController {

    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> comboCategory;
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtQuantity;
    @FXML private Button btnUpdateProduct;
    @FXML private Button btnCancelProduct;

    private final ProductService service = new ProductServiceImpl();
    private Product original;

    @FXML
    public void initialize() {
        if (comboCategory != null) {
            comboCategory.getItems().addAll("Ladies", "Gents", "Kids");
        }
    }

    public void setProductData(Product p) {
        this.original = p;
        txtProductId.setText(p.getProductId());
        txtProductId.setDisable(true); // id not editable
        txtProductName.setText(p.getDescription());
        if (comboCategory != null) comboCategory.setValue(p.getCategoryId());
        txtUnitPrice.setText(String.valueOf(p.getUnitPrice()));
        txtQuantity.setText(String.valueOf(p.getQuantity()));
    }

    @FXML
    void btnUpdateProductOnAction(ActionEvent event) {
        String id = txtProductId.getText().trim();
        String name = txtProductName.getText().trim();
        String cat = (comboCategory == null ? null : comboCategory.getValue());
        String priceStr = txtUnitPrice.getText().trim();
        String qtyStr = txtQuantity.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please fill all required fields.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int qty = Integer.parseInt(qtyStr);

            // Build product with 6-arg constructor; keep supplierId as null
            Product updated = new Product(id, name, price, qty, (cat != null ? cat : null), null);

            boolean ok = service.updateProduct(updated);
            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Updated", "Product updated successfully.");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Product update failed.");
            }
        } catch (NumberFormatException nfe) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Unit price and quantity must be numeric.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnCancelProductOnAction(ActionEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        Stage s = (Stage) btnCancelProduct.getScene().getWindow();
        s.close();
    }

    private void showAlert(Alert.AlertType t, String title, String msg) {
        Alert a = new Alert(t);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
