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
import model.dto.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCategoryFormController implements Initializable {

    public AnchorPane rootPane;
    public ImageView logoImage;
    public Label lblTittle;
    public Label lblSubTitle;
    public Rectangle rectangle1;
    public Label lblCategory;
    public Label lblName;
    public Label lblDescription;
    CategoryService categoryService = new CategoryServiceImpl();

    @FXML
    private Button btnCancelCategory;

    @FXML
    private Button btnUpdateCategory;

    @FXML
    private TextField txtCategoryId;

    @FXML
    private TextField txtCategoryName;

    @FXML
    private TextField txtDescription;
    private Category selectedCategory;

    @FXML
    void btnCancelCategoryOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnUpdateCategoryOnAction(ActionEvent event) {
        try {
            String id = txtCategoryId.getText();
            String name = txtCategoryName.getText();
            String desc = txtDescription.getText();

            if (id.isEmpty() || name.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in all fields!").show();
                return;
            }

            Category category = new Category(id, name, desc);
            boolean isUpdated = categoryService.updateCategory(category);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Category updated successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update category!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error updating category: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void setCategoryData(Category selected) {
        this.selectedCategory = selected;
        txtCategoryId.setText(selected.getCategoryId());
        txtCategoryName.setText(selected.getName());
        txtDescription.setText(selected.getDescription());
    }
    private void clearFields() {
        txtCategoryId.clear();
        txtCategoryName.clear();
        txtDescription.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            adjustLayout(); // initial positioning

            rootPane.widthProperty().addListener((obs, oldVal, newVal) -> adjustLayout());
            rootPane.heightProperty().addListener((obs, oldVal, newVal) -> adjustLayout());
        });
    }

    private void adjustLayout() {
        double paneWidth = rootPane.getWidth();
        double paneHeight = rootPane.getHeight();

        if (paneWidth == 0 || paneHeight == 0) return;

        double centerX = paneWidth / 2;

        // Center rectangle
        rectangle1.setLayoutX(centerX - rectangle1.getWidth() / 2);

        // Align labels and text fields relative to rectangle
        lblCategory.setLayoutX(rectangle1.getLayoutX() + 100);
        txtCategoryId.setLayoutX(lblCategory.getLayoutX() + 120);

        lblName.setLayoutX(txtCategoryId.getLayoutX() + 260);
        txtCategoryName.setLayoutX(lblName.getLayoutX() + 130);

        lblDescription.setLayoutX(rectangle1.getLayoutX() + 110);
        txtDescription.setLayoutX(lblDescription.getLayoutX() + 120);

        // Buttons
        btnUpdateCategory.setLayoutX(centerX - 100);
        btnCancelCategory.setLayoutX(centerX + 20);
    }


}
