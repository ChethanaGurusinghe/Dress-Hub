package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.dto.Category;
import service.CategoryService;
import service.impl.CategoryServiceImpl;

public class AddCategoryFormController {

    CategoryService categoryService = new CategoryServiceImpl();

    @FXML
    private Button btnAddCategory;

    @FXML
    private Button btnCancelCategory;

    @FXML
    private TextField txtCategoryId;

    @FXML
    private TextField txtCategoryName;

    @FXML
    private TextField txtDescription;

    @FXML
    void btnAddCategoryOnAction(ActionEvent event) {

        try {
            String id = txtCategoryId.getText();
            String name = txtCategoryName.getText();
            String desc = txtDescription.getText();

            if (id.isEmpty() || name.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill in Category ID and Name!").show();
                return;
            }

            Category category = new Category(id, name, desc);

            boolean isAdded = categoryService.addCategory(category);
            if (isAdded) {
                new Alert(Alert.AlertType.INFORMATION, "Category added successfully!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add category!").show();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error adding category: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    @FXML
    void btnCancelCategoryOnAction(ActionEvent event) {
        clearFields();

    }
    private void clearFields() {
        txtCategoryId.clear();
        txtCategoryName.clear();
        txtDescription.clear();
    }


}
