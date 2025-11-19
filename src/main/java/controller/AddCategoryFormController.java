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

public class AddCategoryFormController implements Initializable {

    public AnchorPane rootPane1;
    public ImageView logoImage;
    public Label lblTitle;
    public Label lblSubTitle;
    public AnchorPane rootPane2;
    public Label lblCategoryId;
    public Label lblCategoryName;
    public Label lblDescription;
    public Rectangle rectangle;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::centerContent);

        rootPane1.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        rootPane1.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());
    }

    private void centerContent() {

        double W = rootPane1.getWidth();
        double H = rootPane1.getHeight();

        if (W <= 0 || H <= 0) return;

        logoImage.setLayoutX((W - logoImage.getFitWidth()) / 2);

        lblTitle.setLayoutX((W - lblTitle.getPrefWidth()) / 2);
        lblSubTitle.setLayoutX((W - lblSubTitle.getPrefWidth()) / 2);

        rectangle.setWidth(W - 400);
        rectangle.setLayoutX(200);

        rectangle.setHeight(H - 270);
        rectangle.setLayoutY(160);

        rootPane2.setLayoutX((W - rootPane2.getPrefWidth()) / 2);
        rootPane2.setLayoutY((H - rootPane2.getPrefHeight()) / 2);
    }

}
