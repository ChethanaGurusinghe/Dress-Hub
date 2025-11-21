package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.dto.Bill;
import service.BillService;
import service.impl.BillServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class BillController implements Initializable {

    private final BillService billService = new BillServiceImpl();
    public AnchorPane root;
    public ImageView logoImage;
    public Label lblTittle;
    public Label lblAddress;
    public Label lblPhone;
    public Label lblBiil;
    public Label lblDay;
    public Label lblti;
    public Label lblLine1;
    public TableView tblBill;
    public TableColumn colProduct;
    public TableColumn colQty;
    public TableColumn colPrice;
    public Label lblLine2;
    public Label lblTot;
    public Label lblLine3;
    public Label lblThankYou;
    public Label lblShop;

    @FXML
    private Label lblBillNo;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalAmount;

    public boolean createBill(String orderId) {
        try {
            // Generate invoice number
            String invoiceNo = billService.generateInvoiceNo();

            // Create Bill object
            Bill bill = new Bill();
            bill.setInvoiceNo(invoiceNo);
            bill.setOrderId(orderId);
            bill.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            // Save bill
            boolean saved = billService.createBill(bill);

            if (saved) {
                System.out.println("Bill saved successfully: " + bill);

                // Update GUI labels
                if (lblBillNo != null) lblBillNo.setText(invoiceNo);

                DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");

                if (lblDate != null) lblDate.setText(LocalDate.now().format(dateFmt));
                if (lblTime != null) lblTime.setText(LocalTime.now().format(timeFmt));
            } else {
                System.out.println("Failed to save bill.");
            }

            return saved;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setTotalAmountLabel(double total) {
        if (lblTotalAmount != null) {
            lblTotalAmount.setText(String.format("%.2f", total));
        }
    }

    public String generateInvoiceNo() {
        try {
            return billService.generateInvoiceNo();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        centerContent();

        root.widthProperty().addListener((obs, oldVal, newVal) -> centerContent());
        root.heightProperty().addListener((obs, oldVal, newVal) -> centerContent());
    }

    private void centerContent() {

        double width = root.getWidth();
        double height = root.getHeight();

        if (width == 0 || height == 0) return;

        double centerX = width / 2;

        // --- CENTER HEADER BLOCK ---
        logoImage.setLayoutX(centerX - (logoImage.getFitWidth() / 2));

        lblTittle.setLayoutX(centerX - (lblTittle.getWidth() / 2));
        lblAddress.setLayoutX(centerX - (lblAddress.getWidth() / 2));
        lblPhone.setLayoutX(centerX - (lblPhone.getWidth() / 2));

        // --- BILL DETAILS BLOCK ---
        double billBlockX = centerX - 120;

        lblBiil.setLayoutX(billBlockX);
        lblBillNo.setLayoutX(billBlockX + 170);

        lblDay.setLayoutX(billBlockX);
        lblDate.setLayoutX(billBlockX + 170);

        lblti.setLayoutX(billBlockX);
        lblTime.setLayoutX(billBlockX + 170);

        // --- LINES ---
        lblLine1.setLayoutX(centerX - 160);
        lblLine2.setLayoutX(centerX - 160);
        lblLine3.setLayoutX(centerX - 160);

        // --- TABLE ---
        tblBill.setLayoutX(centerX - (tblBill.getPrefWidth() / 2));

        // --- TOTAL BLOCK ---
        lblTot.setLayoutX(centerX + 20);
        lblTotalAmount.setLayoutX(centerX + 120);

        // --- FOOTER ---
        lblThankYou.setLayoutX(centerX - (lblThankYou.getWidth() / 2));
        lblShop.setLayoutX(centerX - (lblShop.getWidth() / 2));
    }
}
