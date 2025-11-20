package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.dto.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateProductFormController implements Initializable {

    public AnchorPane rootPane;
    public ImageView logoImage;
    public Label lblTitle;
    public Label lblSubTitle;
    public Rectangle rectangle1;
    public Label lblProductId;
    public Label lblProductName;
    public Label lblCategory;
    public Label lblPrice;
    public Label lblQty;
    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> comboCategory;
    @FXML private TextField txtUnitPrice;
    @FXML private TextField txtQuantity;
    @FXML private Button btnUpdateProduct;
    @FXML private Button btnCancelProduct;

    private final ProductService service = new ProductServiceImpl();
    private Product original;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            adjustLayout(); // initial positioning

            rootPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustLayout());
            rootPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustLayout());
        });

        if (comboCategory != null) {
            comboCategory.getItems().addAll("Ladies", "Gents", "Kids");
        }
    }

    private void adjustLayout() {
        double paneWidth = rootPane.getWidth();
        double paneHeight = rootPane.getHeight();

        if (paneWidth == 0 || paneHeight == 0) return;

        double centerX = paneWidth / 2;

        // Center rectangle
        rectangle1.setLayoutX(centerX - rectangle1.getWidth() / 2);

        lblProductId.setLayoutX(rectangle1.getLayoutX() + 100);
        txtProductId.setLayoutX(lblProductId.getLayoutX() + 110);

        lblProductName.setLayoutX(txtProductId.getLayoutX() + 280);
        txtProductName.setLayoutX(lblProductName.getLayoutX() + 120);

        lblCategory.setLayoutX(rectangle1.getLayoutX() + 110);
        comboCategory.setLayoutX(lblCategory.getLayoutX() + 110);

        lblPrice.setLayoutX(txtProductName.getLayoutX());
        txtUnitPrice.setLayoutX(lblPrice.getLayoutX() + 120);

        lblQty.setLayoutX(txtProductName.getLayoutX());
        txtQuantity.setLayoutX(lblQty.getLayoutX() + 120);

        // Buttons
        btnUpdateProduct.setLayoutX(centerX - 100);
        btnCancelProduct.setLayoutX(centerX + 20);
    }

}
