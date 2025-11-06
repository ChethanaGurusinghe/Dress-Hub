package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dto.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

public class AddProductFormController {

    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> comboCategory; // fx:id matches FXML
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtQuantity;
    @FXML private Button btnAddProduct;
    @FXML private Button btnCancelProduct;

    private final ProductService service = new ProductServiceImpl();

    @FXML
    public void initialize() {
        // ensure the fx:id is correct in FXML; populate categories (you used them in UI)
        if (comboCategory != null) {
            comboCategory.getItems().addAll("Ladies", "Gents", "Kids");
        }
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        String id = txtProductId.getText().trim();
        String name = txtProductName.getText().trim();
        String cat = (comboCategory == null ? null : comboCategory.getValue());
        String unitPriceStr = txtUnitPrice.getText().trim();
        String qtyStr = txtQuantity.getText().trim();

        if (id.isEmpty() || name.isEmpty() || unitPriceStr.isEmpty() || qtyStr.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation", "Please fill all required fields.");
            return;
        }

        try {
            double price = Double.parseDouble(unitPriceStr);
            int qty = Integer.parseInt(qtyStr);

            // Product constructor in your model expects 6 args:
            // Product(String productId, String description, double unitPrice, int quantity, String categoryId, String supplierId)
            Product p = new Product(id, name, price, qty, (cat != null ? cat : null), null);

            boolean ok = service.addProduct(p);
            if (ok) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Product added.");
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Product not added (maybe duplicate id).");
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
