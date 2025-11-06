package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dto.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;

public class UpdateCategoryFormController {

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
}
