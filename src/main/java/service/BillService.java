package service;

import model.dto.Bill;

import java.sql.SQLException;

public interface BillService {

    boolean createBill(Bill bill) throws SQLException;
    String generateInvoiceNo() throws SQLException;
}
