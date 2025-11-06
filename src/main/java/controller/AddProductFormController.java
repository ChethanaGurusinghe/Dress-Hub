package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.dto.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class AddProductFormController implements Initializable {

    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> comboCategory; // fx:id matches FXML
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtQuantity;
    @FXML private Button btnAddProduct;
    @FXML private Button btnCancelProduct;

    private final ProductService service = new ProductServiceImpl();

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
            String id = txtProductId.getText().trim();
            String name = txtProductName.getText().trim();
            String catName = comboCategory.getValue();
            String unitPriceStr = txtUnitPrice.getText().trim();
            String qtyStr = txtQuantity.getText().trim();

            if (id.isEmpty() || name.isEmpty() || catName == null || unitPriceStr.isEmpty() || qtyStr.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Validation", "Please fill all required fields.");
                return;
            }

            try {
                double price = Double.parseDouble(unitPriceStr);
                int qty = Integer.parseInt(qtyStr);

                // Map category name to category ID
                String categoryId = switch (catName) {
                    case "Ladies" -> "I001";
                    case "Gents" -> "I002";
                    case "Kids" -> "I003";
                    default -> null;
                };

                if (categoryId == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid category selected!");
                    return;
                }

                Product p = new Product(id, name, price, qty, categoryId, null);

                boolean ok = service.addProduct(p);
                if (ok) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully!");
                    closeWindow();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed", "Product not added (maybe duplicate ID).");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ensure the fx:id is correct in FXML; populate categories (you used them in UI)
        if (comboCategory != null) {
            comboCategory.getItems().addAll("Ladies", "Gents", "Kids");
        }
    }
}
