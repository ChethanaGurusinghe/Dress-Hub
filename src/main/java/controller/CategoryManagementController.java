package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.dto.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CategoryManagementController {

    public Button btnUserManagement;
    public AnchorPane rootPane;
    public Rectangle rectangle1;
    public ImageView logoImage;
    public Label lblTittle;
    public Rectangle rectangle2;
    public Text lblHeader;
    // Sidebar buttons (fx:id must match your FXML)
    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnEmployeeManagement;
    @FXML private Button btnSupplierManagement;
    @FXML private Button btnAdminManagement;
    @FXML private Button btnCategoryManagement;
    @FXML private Button btnLogOut;

    @FXML private Button btnAddCategory;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    @FXML private TextField searchTxtFeild;

    @FXML private TableView<Category> tblCategory;
    @FXML private TableColumn<Category, String> colCategoryId;
    @FXML private TableColumn<Category, String> colCategoryName;
    @FXML private TableColumn<Category, String> colDescription;

    private final CategoryService service = new CategoryServiceImpl();
    private final ObservableList<Category> categoryList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind table columns to Category properties
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Load initial data
        refreshTable();

        // Live search (by ID or name)
        searchTxtFeild.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                tblCategory.setItems(categoryList);
            } else {
                String keyword = newVal.trim().toLowerCase();
                ObservableList<Category> filtered = FXCollections.observableArrayList();
                for (Category c : categoryList) {
                    boolean matches = (c.getCategoryId() != null && c.getCategoryId().toLowerCase().contains(keyword))
                            || (c.getName() != null && c.getName().toLowerCase().contains(keyword));
                    if (matches) filtered.add(c);
                }
                tblCategory.setItems(filtered);
            }
        });
    }

    private void refreshTable() {
        try {
            List<Category> all = service.getAllCategories();
            categoryList.setAll(all);
            tblCategory.setItems(categoryList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Could not load categories: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddCategoryOnAction(ActionEvent event) {
        try {
            openFormModal("/view/AddCategoryForm.fxml", "Add Category");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open Add Category form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        Category selected = tblCategory.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a category to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateCategoryForm.fxml"));
            Parent root = loader.load();

            UpdateCategoryFormController ctrl = loader.getController();
            ctrl.setCategoryData(selected);

            Stage stage = new Stage();
            stage.setTitle("Update Category");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open Update Category form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Category selected = tblCategory.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a category to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete category " + selected.getCategoryId() + " ?");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirm.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == yes) {
            try {
                boolean ok = service.deleteCategory(selected.getCategoryId());
                if (ok) {
                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "Category deleted successfully.");
                    refreshTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "Could not delete category.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting category: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void openFormModal(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        // safe owner retrieval
        if (btnAddCategory != null && btnAddCategory.getScene() != null) {
            stage.initOwner(btnAddCategory.getScene().getWindow());
        }
        stage.showAndWait();
        refreshTable();
    }

    public void btnAdminDashboardOnAction(ActionEvent event) {
        navigate(event, "/view/AdminDashboard.fxml", "Dashboard");
    }

    public void btnProdutManagementOnAction(ActionEvent event) {
        navigate(event, "/view/ProductManagement.fxml", "Product Management");
    }

    public void btnUserManagementOnAction(ActionEvent event) {
        navigate(event, "/view/UserManagement.fxml", "User Management");
    }

    public void btnSupplierManagementOnAction(ActionEvent event) {
        navigate(event, "/view/SupplierManagement.fxml", "Supplier Management");
    }

    public void btnCategoryManagementOnAction(ActionEvent event) {
        //current page;
        refreshTable();
    }

    public void btnLogOutOnAction(ActionEvent event) {
        handleLogout(event);
    }

    private void navigate(ActionEvent event, String fxml, String title) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxml))));
            stage.setTitle(title);
            stage.show();

            if (event != null && event.getSource() instanceof Button) {
                Stage current = (Stage) ((Button) event.getSource()).getScene().getWindow();
                current.close();
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
            e.printStackTrace();
        }
    }

    // Alert
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
