package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class AddUserFormController {

    public Button btnAddUser;
    public Button btnCancelUser;
    UserService service = new UserServiceImpl();

    @FXML
    private Button btnAddEmployee;

    @FXML
    private Button btnCancelEmployee;

    @FXML
    private TextField txtProductId;

    @FXML
    private TextField txtProductId1;

    @FXML
    private TextField txtProductName;

    @FXML
    private TextField txtProductName1;


    public void btnAddEmployeeOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelEmployeeOnAction(ActionEvent actionEvent) {
    }

    public void btnAddUserOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelUserOnAction(ActionEvent actionEvent) {
    }
}
