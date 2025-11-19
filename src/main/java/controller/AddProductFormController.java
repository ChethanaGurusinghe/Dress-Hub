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

public class AddProductFormController implements Initializable {

    public ImageView logoImage;
    public Label lblTittle;
    public Label lblSubTitle;
    public Label lblProductId;
    public Label lblProductName;
    public Label lblCategory;
    public Label lblUnitPrice;
    public Label lblQuantity;
    public AnchorPane root;
    public Rectangle rectangle;
    @FXML private TextField txtProductId;
    @FXML private TextField txtProductName;
    @FXML private ComboBox<String> comboCategory;
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

        Platform.runLater(() -> {
            centerContent();

            // Listen to resizing
            root.widthProperty().addListener((obs, oldV, newV) -> centerContent());
            root.heightProperty().addListener((obs, oldV, newV) -> centerContent());
        });

        // ensure the fx:id is correct in FXML; populate categories (you used them in UI)
        if (comboCategory != null) {
            comboCategory.getItems().addAll("Ladies", "Gents", "Kids");
        }
    }

    private void centerContent() {

        double paneWidth = root.getWidth();
        double paneHeight = root.getHeight();

        if (paneWidth == 0 || paneHeight == 0) return;

        // ---------- Logo ----------
        logoImage.setLayoutX((paneWidth - logoImage.getFitWidth()) / 2);
        logoImage.setLayoutY(20);

        // ---------- Title ----------
        lblTittle.setLayoutX((paneWidth - lblTittle.getWidth()) / 2);
        lblTittle.setLayoutY(logoImage.getLayoutY() + 80);

        // ---------- Main Rectangle ----------
        double rectWidth = paneWidth * 0.75;     // 75% of window width
        double rectHeight = paneHeight * 0.65;   // 65% of window height

        rectangle.setWidth(rectWidth);
        rectangle.setHeight(rectHeight);
        rectangle.setLayoutX((paneWidth - rectWidth) / 2);
        rectangle.setLayoutY((paneHeight - rectHeight) / 2);

        // ---------- Subtitle ----------
        lblSubTitle.setLayoutX((paneWidth - lblSubTitle.getWidth()) / 2);
        lblSubTitle.setLayoutY(rectangle.getLayoutY() - 30);

        // Distance from left of rectangle
        double leftX = rectangle.getLayoutX() + 110;
        double rightX = rectangle.getLayoutX() + rectWidth - 350;

        // ---------- Product ID ----------
        lblProductId.setLayoutX(leftX);
        txtProductId.setLayoutX(leftX + 110);

        lblProductName.setLayoutX(rightX);
        txtProductName.setLayoutX(rightX + 120);

        // ---------- Category & Unit Price ----------
        lblCategory.setLayoutX(leftX);
        comboCategory.setLayoutX(leftX + 110);

        lblUnitPrice.setLayoutX(rightX);
        txtUnitPrice.setLayoutX(rightX + 120);

        // ---------- Quantity ----------
        lblQuantity.setLayoutX(rightX);
        txtQuantity.setLayoutX(rightX + 120);

        // ---------- Buttons ----------
        btnAddProduct.setLayoutX(paneWidth / 2 - 120);
        btnCancelProduct.setLayoutX(paneWidth / 2 + 20);
    }

}
