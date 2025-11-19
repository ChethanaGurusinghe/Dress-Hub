package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import model.dto.Product;
import service.ProductService;
import service.impl.ProductServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductManagementController implements Initializable {

    public Button btnUserManagement;
    public AnchorPane rootPane;
    public Rectangle rectangle1;
    public ImageView logoImage;
    public Label lblTittle;
    public Rectangle rectangle2;
    public Text lblHeader;

    @FXML
    private Button btnAdminDashboard;

    @FXML
    private Button btnProductManagement;

    @FXML
    private Button btnEmployeeManagement;

    @FXML
    private Button btnSupplierManagement;

    @FXML
    private Button btnAdminManagement;

    @FXML
    private Button btnCategoryManagement;

    @FXML
    private Button btnLogOut;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField searchTxtFeild;

    @FXML
    private TableView<Product> tblProducts;

    @FXML
    private TableColumn<Product, String> colProductId;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, String> colCategory;

    @FXML
    private TableColumn<Product, Double> colUnitPrice;

    @FXML
    private TableColumn<Product, Integer> colQtyInInventory;

    private final ProductService service = new ProductServiceImpl();
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    private void refreshTable() {
        try {
            List<Product> all = service.getAllProducts();
            productList.setAll(all);
            tblProducts.setItems(productList);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error", "Could not load products: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddProductOnAction(ActionEvent event) {
        try {
            openFormModal("/view/AddProductForm.fxml", "Add Product");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open Add Product form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditOnAction(ActionEvent event) {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to edit.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateProductForm.fxml"));
            Parent root = loader.load();

            // pass selected product to controller
            UpdateProductFormController ctrl = loader.getController();
            ctrl.setProductData(selected);

            Stage stage = new Stage();
            stage.setTitle("Update Product");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTable();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Unable to open Update Product form: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        Product selected = tblProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a product to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete product " + selected.getProductId() + " ?");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirm.getButtonTypes().setAll(yes, no);

        Optional<ButtonType> res = confirm.showAndWait();
        if (res.isPresent() && res.get() == yes) {
            try {
                boolean ok = service.deleteProduct(selected.getProductId());
                if (ok) {
                    showAlert(Alert.AlertType.INFORMATION, "Deleted", "Product deleted successfully.");
                    refreshTable();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Failed", "Could not delete product.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting product: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void openFormModal(String fxml, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initOwner(btnAddProduct.getScene().getWindow());
        stage.showAndWait();
        refreshTable();
    }

    // Sidebar navigation methods (use ActionEvent parameter correctly)
    public void btnAdminDashboardOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminDashboard.fxml"))));
            stage.setTitle("Dashboard");
            stage.show();

            // close current (if called from a button)
            Stage current = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            current.close();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnProdutManagementOnAction(ActionEvent actionEvent) {
        // you are already in product management; do nothing or refresh
        refreshTable();
    }

    public void btnSupplierManagementOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SupplierManagement.fxml"))));
            stage.setTitle("Supplier Management");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnCategoryManagementOnAction(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CategoryManagement.fxml"))));
            stage.setTitle("Category Management");
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", e.getMessage());
            e.printStackTrace();
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {
        handleLogout(actionEvent);
    }

    private void showAlert(Alert.AlertType t, String title, String msg) {
        Alert a = new Alert(t);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // match Product getter names (getProductId, getDescription, getCategory, getUnitPrice, getQuantity)
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category")); // ensure getCategory() exists
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyInInventory.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // load from service
        refreshTable();

        // live search
        searchTxtFeild.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.trim().isEmpty()) {
                tblProducts.setItems(productList);
            } else {
                String k = newVal.trim().toLowerCase();
                ObservableList<Product> filtered = FXCollections.observableArrayList();
                for (Product p : productList) {
                    boolean matches = (p.getProductId() != null && p.getProductId().toLowerCase().contains(k))
                            || (p.getDescription() != null && p.getDescription().toLowerCase().contains(k));
                    if (matches) filtered.add(p);
                }
                tblProducts.setItems(filtered);
            }
        });
    }

    private void centerContent() {
        double width = rootPane.getWidth();
        double height = rootPane.getHeight();

        if (width == 0 || height == 0) return;

        rectangle1.setHeight(height);

        btnAdminDashboard.setLayoutX(50);
        btnProductManagement.setLayoutX(50);
        btnUserManagement.setLayoutX(50);
        btnSupplierManagement.setLayoutX(50);
        btnCategoryManagement.setLayoutX(50);
        btnLogOut.setLayoutX(59);

        rectangle2.setWidth(width - 350);
        lblHeader.setLayoutX(350 + (rectangle2.getWidth() / 2 - lblHeader.getBoundsInParent().getWidth() / 2));

        searchTxtFeild.setLayoutX(520);
        btnAddProduct.setLayoutX(width - 320);

        tblProducts.setLayoutX(350 + ((width - 350 - tblProducts.getPrefWidth()) / 2));

        btnEdit.setLayoutX(tblProducts.getLayoutX() + 200);
        btnDelete.setLayoutX(tblProducts.getLayoutX() + 380);
    }
}
