package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.dto.User;
import service.UserService;
import service.impl.UserServiceImpl;

public class UpdateUserFormController {

    public ComboBox comboRole;
    UserService service = new UserServiceImpl();

    @FXML
    private Button btnEmpCancel;

    @FXML
    private Button btnEmpUpdate;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtPhoneNo;

    @FXML
    void btnEmpCancelOnAction(ActionEvent event) {

    }

    @FXML
    public void btnEmpUpdateOnAction(ActionEvent actionEvent) {

    }
}

