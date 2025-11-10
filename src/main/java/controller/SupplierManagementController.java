package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    public TextField txtSearch;
    SupplierRepository repo = new SupplierRepositoryImpl();

    public TableView tblSupplier;
    public Button btnUserManagement;
    @FXML
    private Button btnAddSupplier;

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnSupplierManagement;

    @FXML
    private TableColumn<?, ?> colCompanyName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNotes;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TextField searchTxtFeild;

    Stage addSupplierStage = new Stage();
    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddSupplierForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Add Supplier");
        stage.showAndWait();
        loadTable();
    }

    Stage adminDashboardStage = new Stage();
    @FXML
    void btnAdminDashboardOnAction(ActionEvent event) {
        try {
            adminDashboardStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminDashboardStage.show();
    }

    Stage categorySatge = new Stage();
    @FXML
    void btnCategoryManagementOnAction(ActionEvent event) {
        try {
            categorySatge.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CategoryManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        categorySatge.show();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Supplier selected = (Supplier) tblSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a supplier to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setContentText("Are you sure you want to delete supplier " + selected.getSupCode() + " ?");
        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == ButtonType.OK) {
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

    Stage editSupplierStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        Supplier selected = (Supplier) tblSupplier.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a supplier to update.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateSupplierForm.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UpdateSupplierFormController controller = loader.getController();
        controller.setSupplier(selected);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Update Supplier");
        stage.showAndWait();
        loadTable();
    }


    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    Stage productManagementstage = new Stage();
    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {

        try {
            productManagementstage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProductManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productManagementstage.show();
    }

    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {

    }

    Stage userStage = new Stage();
    public void btnUserManagementOnAction(ActionEvent actionEvent) {
        try {
            userStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UserManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userStage.show();
    }

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

    public void txtSearchOnAction(ActionEvent event) {
        String code = txtSearch.getText().trim();
        if (code.isEmpty()) {
            loadTable();
            return;
        }

        try {
            Supplier s = repo.searchSupplier(code);
            if (s != null) {
                tblSupplier.setItems(FXCollections.observableArrayList(s));
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Not Found", "No supplier found for code: " + code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
