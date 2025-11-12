package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dto.Supplier;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static util.AlertUtils.showAlert;

public class SupplierManagementController {

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TableColumn<Supplier, String> colSupplierId;

    @FXML
    private TableColumn<Supplier, String> colCompanyName;

    @FXML
    private TableColumn<Supplier, String> colSupplierName;

    @FXML
    private TableColumn<Supplier, String> colPhoneNo;

    @FXML
    private TableColumn<Supplier, String> colEmail;

    @FXML
    private TableColumn<Supplier, String> colNotes;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUserManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnSupplierManagement;

    @FXML
    private Button btnLogOut;

    private final SupplierRepository repo = new SupplierRepositoryImpl();

    // --- Navigation buttons ---
    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddSupplierForm.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Supplier");
            stage.showAndWait();
            loadTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        Supplier selected = tblSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a supplier to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateSupplierForm.fxml"));
            Parent root = loader.load();
            UpdateSupplierFormController controller = loader.getController();
            controller.setSupplier(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Supplier");
            stage.showAndWait();
            loadTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Supplier selected = tblSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a supplier to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setContentText("Are you sure you want to delete supplier " + selected.getSupCode() + "?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (repo.deleteSupplier(selected.getSupCode())) {
                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "Supplier deleted successfully.");
                    loadTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete supplier.");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {
        openNewWindow("/view/AdminDashboard.fxml", "Admin Dashboard");
    }

    @FXML
    void btnCategoryManagementOnAction(ActionEvent event) {
        openNewWindow("/view/CategoryManagement.fxml", "Category Management");
    }

    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {
        openNewWindow("/view/ProductManagement.fxml", "Product Management");
    }

    @FXML
    void btnUserManagementOnAction(ActionEvent event) {
        openNewWindow("/view/UserManagement.fxml", "User Management");
    }

    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {
        // current page
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
    }

    private void openNewWindow(String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // --- Initialize ---
    @FXML
    public void initialize() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supCode"));
        colCompanyName.setCellValueFactory(new PropertyValueFactory<>("compName"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));

        loadTable();
    }

    private void loadTable() {
        try {
            List<Supplier> list = repo.getAllSuppliers();
            tblSupplier.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String code = txtSearch.getText().trim();
        if (code.isEmpty()) {
            loadTable();
            return;
        }

        try {
            Supplier supplier = repo.searchSupplier(code);
            if (supplier != null) {
                tblSupplier.setItems(FXCollections.observableArrayList(supplier));
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "No supplier found for code: " + code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                try {
                    // Load LoginForm.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginForm.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.centerOnScreen();
                    stage.setTitle("Login");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // If "No" is selected, do nothing
        });
    }
}
