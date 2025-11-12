package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderPlacementFormController {

    @FXML
    public Button btnPlaceOrder;

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

    Stage printStage = new Stage();
    @FXML
    void btnInvoicePrintOnAction(ActionEvent event) {
        try {
            printStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Bill.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        printStage.show();
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {

    }

    @FXML
    public void btnPlaceOrderOnAction(ActionEvent event) {
    }
}
