package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.dto.Supplier;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;

import java.net.URL;
import java.util.ResourceBundle;

import static util.AlertUtils.showAlert;

public class AddSupplierFormController implements Initializable {

    public ImageView logoImage;
    public Label lblTittle;
    public Label lblSubTittle;
    public Rectangle rectangle;
    public Label lblSupplierId;
    public Label lblCompanyName;
    public Label lblSupName;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblNotes;
    public AnchorPane root;
    SupplierRepository repo = new SupplierRepositoryImpl();

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnCancelSupplier;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNotes;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Supplier s = new Supplier(
                txtSupplierId.getText(),
                txtCompanyName.getText(),
                txtSupplierName.getText(),
                txtPhoneNo.getText(),
                txtEmail.getText(),
                txtNotes.getText()
        );

        try {
            if (repo.addSupplier(s)) {
                showAlert(Alert.AlertType.INFORMATION, "Saved", "Supplier added successfully!");
                closeForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add supplier.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    private void closeForm() {

        ((Stage) txtSupplierId.getScene().getWindow()).close();
    }

    @FXML
    void btnCancelSupplierOnAction(ActionEvent event) {

        closeForm();
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            centerContent();

            root.widthProperty().addListener((o, oldV, newV) -> centerContent());
            root.heightProperty().addListener((o, oldV, newV) -> centerContent());
        });
    }

    private void centerContent() {

        double paneW = root.getWidth();
        double paneH = root.getHeight();

        if (paneW == 0 || paneH == 0) return;

        // ============ LOGO ============
        logoImage.setLayoutX((paneW - logoImage.getFitWidth()) / 2);
        logoImage.setLayoutY(20);

        // ============ TITLE ============
        lblTittle.setLayoutX((paneW - lblTittle.getWidth()) / 2);
        lblTittle.setLayoutY(logoImage.getLayoutY() + 80);

        // ============ SUBTITLE ============
        lblSubTittle.setLayoutX((paneW - lblSubTittle.getWidth()) / 2);
        lblSubTittle.setLayoutY(lblTittle.getLayoutY() + 60);

        // ============ MAIN RECTANGLE ============
        double rectW = paneW * 0.75;
        double rectH = paneH * 0.63;

        rectangle.setWidth(rectW);
        rectangle.setHeight(rectH);

        rectangle.setLayoutX((paneW - rectW) / 2);
        rectangle.setLayoutY((paneH - rectH) / 2);

        // Left & Right column X values inside the rectangle
        double leftX = rectangle.getLayoutX() + 120;
        double rightX = rectangle.getLayoutX() + rectW - 380;

        // Vertical spacing
        double row1Y = rectangle.getLayoutY() + 50;
        double row2Y = row1Y + 70;
        double row3Y = row2Y + 70;
        double row4Y = row3Y + 70;

        // ============ ROW 1 ============
        lblSupplierId.setLayoutX(leftX);
        txtSupplierId.setLayoutX(leftX + 120);

        lblCompanyName.setLayoutX(rightX);
        txtCompanyName.setLayoutX(rightX + 145);

        // ============ ROW 2 ============
        lblSupName.setLayoutX(leftX);
        txtSupplierName.setLayoutX(leftX + 120);

        lblPhone.setLayoutX(rightX);
        txtPhoneNo.setLayoutX(rightX + 145);

        // ============ ROW 3 ============
        lblEmail.setLayoutX(leftX);
        txtEmail.setLayoutX(leftX + 120);

        lblNotes.setLayoutX(rightX);
        txtNotes.setLayoutX(rightX + 145);

        // ============ BUTTONS ============
        btnAddSupplier.setLayoutX((paneW / 2) - 120);
        btnCancelSupplier.setLayoutX((paneW / 2) + 20);

        // Buttons Y (fixed relative to rectangle)
        btnAddSupplier.setLayoutY(rectangle.getLayoutY() + rectH - 70);
        btnCancelSupplier.setLayoutY(rectangle.getLayoutY() + rectH - 70);
    }

}
