package controller;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EmployeeManagementController {

    UserService service = new UserServiceImpl();
    ObservableList<User> employeeList = FXCollections.observableArrayList();

    @FXML
    private Button btnAddProduct;

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
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colPhoneNo;

    @FXML
    private TextField searchTxtFeild;

    @FXML
    private TableView<User> tblEmployee;

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

    Stage adminStage = new Stage();
    @FXML
    void btnAdminManagementOnAction(ActionEvent event) {
        try {
            adminStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        adminStage.show();
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
        User selected = tblEmployee.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an employee to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete employee " + selected.getUserId() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean success = service.deleteUser(selected.getUserId());
                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "Employee deleted successfully.");
                    refreshTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "Could not delete employee.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }

    Stage editEmployeeStage = new Stage();
    @FXML
    void btnEditOnAction(ActionEvent event) {
        User selected = tblEmployee.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an employee to update.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateEmployeeForm.fxml"));
            Parent root = loader.load();

            UpdateEmployeeFormController ctrl = loader.getController();
            ctrl.setEmployeeData(selected);

            Stage stage = new Stage();
            stage.setTitle("Update Employee");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    void btnEmployeeManagementOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }
    Stage productStage = new Stage();
    @FXML
    void btnProdutManagementOnAction(ActionEvent event) {
        try {
            productStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ProductManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productStage.show();
    }

    Stage supplierStage = new Stage();
    @FXML
    void btnSupplierManagementOnAction(ActionEvent event) {
        try {
            supplierStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SupplierManagement.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        supplierStage.show();
    }

    Stage addEmployeeStage = new Stage();
    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {

        try {
            addEmployeeStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddEmployeeForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addEmployeeStage.show();
    }

    @FXML
    public void initialize() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPhoneNo.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        refreshTable();

        searchTxtFeild.textProperty().addListener((obs, oldVal, newVal) -> filterEmployees(newVal));
    }

    private void refreshTable() {
        try {
            List<User> all = service.getUsersByRole("employee");
            employeeList.setAll(all);
            tblEmployee.setItems(employeeList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Failed to load employees: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void filterEmployees(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            tblEmployee.setItems(employeeList);
            return;
        }
        String lower = keyword.toLowerCase();
        ObservableList<User> filtered = FXCollections.observableArrayList();
        for (User u : employeeList) {
            if ((u.getUserId() != null && u.getUserId().toLowerCase().contains(lower))
                    || (u.getFullName() != null && u.getFullName().toLowerCase().contains(lower))) {
                filtered.add(u);
            }
        }
        tblEmployee.setItems(filtered);
    }

    private void openModal(String fxml, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
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
