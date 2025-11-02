package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddAdminFormController {

    @FXML
    public TextField txtAdminId;

    @FXML
    public TextField txtAdminName;

    @FXML
    public TextField txtEmail;

    @FXML
    public Button btnUpdateAdmin;

    @FXML
    private Button btnAddAdmin;

    @FXML
    private Button btnCancelAdmin;

    @FXML
    private TextField txtPhoneNo;


    @FXML
    void btnAddAdminOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelAdminOnAction(ActionEvent event) {

    }


}
