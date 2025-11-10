package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.Supplier;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;

import static util.AlertUtils.showAlert;

public class UpdateSupplierFormController {

    SupplierRepository repo = new SupplierRepositoryImpl();

    private Supplier currentSupplier;
    @FXML
    private Button btnCancelSupplier;

    @FXML
    private Button btnUpdateSupplier;

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
    private Supplier supplier;

    @FXML
    void btnCancelSupplierOnAction(ActionEvent event) {
        closeForm();
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        currentSupplier.setCompName(txtCompanyName.getText());
        currentSupplier.setSupName(txtSupplierName.getText());
        currentSupplier.setPhone(txtPhoneNo.getText());
        currentSupplier.setEmail(txtEmail.getText());
        currentSupplier.setNotes(txtNotes.getText());

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

    private void closeForm() {
        ((Stage) txtSupplierId.getScene().getWindow()).close();
    }

    public void setSupplier(Supplier selected) {

        this.currentSupplier = supplier;
        txtSupplierId.setText(supplier.getSupCode());
        txtCompanyName.setText(supplier.getCompName());
        txtSupplierName.setText(supplier.getSupName());
        txtPhoneNo.setText(supplier.getPhone());
        txtEmail.setText(supplier.getEmail());
        txtNotes.setText(supplier.getNotes());
        txtSupplierId.setEditable(false);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
