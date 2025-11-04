package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class OrderPlacementFormController {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnInvoicePrint;

    @FXML
    private Button btnLogOut;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblProductName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<?> tblCart;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtQuantity;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnInvoicePrintOnAction(ActionEvent event) {

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

}
