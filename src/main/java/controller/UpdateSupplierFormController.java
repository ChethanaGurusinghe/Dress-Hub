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

    private final SupplierRepository repo = new SupplierRepositoryImpl();
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
}
