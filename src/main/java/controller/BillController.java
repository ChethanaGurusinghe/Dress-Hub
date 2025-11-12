package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.dto.Bill;
import service.BillService;
import service.impl.BillServiceImpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BillController {

    private final BillService billService = new BillServiceImpl();

    @FXML
    private Label lblBillNo;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblTotalAmount;

    /**
     * Create and persist a bill for the given orderId.
     * @param orderId Must exist in the orders table
     * @return true if saved successfully
     */
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
}
