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

public class AddSupplierFormController {

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

}
