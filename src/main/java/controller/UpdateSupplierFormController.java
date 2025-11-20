package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.dto.Supplier;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;

import java.net.URL;
import java.util.ResourceBundle;

import static util.AlertUtils.showAlert;

public class UpdateSupplierFormController implements Initializable {

    private final SupplierRepository repo = new SupplierRepositoryImpl();
    public AnchorPane rootPane;
    public ImageView logoImage;
    public Label lblTitle;
    public Label lblSubTitle;
    public Rectangle rectangle1;
    public Label lblSupId;
    public Label lblCompName;
    public Label lblSupName;
    public Label lblPhone;
    public Label lblEmail;
    public Label lblNotes;

    private double xOffset = 0;
    private double yOffset = 0;
    private boolean isMaximized = false;

    private double prevX, prevY, prevWidth, prevHeight;
    private Supplier currentSupplier;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtCompanyName;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNotes;

    @FXML
    private Button btnUpdateSupplier;

    @FXML
    private Button btnCancelSupplier;

    @FXML
    void btnCancelSupplierOnAction(ActionEvent event) {
        closeForm();
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        if (currentSupplier == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No supplier selected.");
            return;
        }

        currentSupplier.setCompName(txtCompanyName.getText().trim());
        currentSupplier.setSupName(txtSupplierName.getText().trim());
        currentSupplier.setPhone(txtPhoneNo.getText().trim());
        currentSupplier.setEmail(txtEmail.getText().trim());
        currentSupplier.setNotes(txtNotes.getText().trim());

        try {
            if (repo.updateSupplier(currentSupplier)) {
                showAlert(Alert.AlertType.INFORMATION, "Updated", "Supplier updated successfully!");
                closeForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update supplier.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    public void setSupplier(Supplier selected) {
        this.currentSupplier = selected;
        txtSupplierId.setText(selected.getSupCode());
        txtCompanyName.setText(selected.getCompName());
        txtSupplierName.setText(selected.getSupName());
        txtPhoneNo.setText(selected.getPhone());
        txtEmail.setText(selected.getEmail());
        txtNotes.setText(selected.getNotes());
        txtSupplierId.setEditable(false);
    }

    private void closeForm() {
        Stage stage = (Stage) txtSupplierId.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Window Drag
        rootPane.setOnMousePressed(event -> {
            if (!isMaximized) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        rootPane.setOnMouseDragged(event -> {
            if (!isMaximized) {
                Stage stage = (Stage) rootPane.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        // Double-click Maximize/Restore
        rootPane.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                toggleMaximize();
            }
        });
    }

    private void toggleMaximize() {
        Stage stage = (Stage) rootPane.getScene().getWindow();

        if (!isMaximized) {
            // save previous size
            prevX = stage.getX();
            prevY = stage.getY();
            prevWidth = stage.getWidth();
            prevHeight = stage.getHeight();

            // maximize the window
            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());

            isMaximized = true;

        } else {
            // restore previous size
            stage.setX(prevX);
            stage.setY(prevY);
            stage.setWidth(prevWidth);
            stage.setHeight(prevHeight);

            isMaximized = false;
        }
    }

}
