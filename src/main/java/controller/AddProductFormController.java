package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddProductFormController {

    @FXML
    private Button btnAddProduct;

    @FXML
    private Button btnCancelProduct;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddProductOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelProductOnAction(ActionEvent event) {

    }

}
