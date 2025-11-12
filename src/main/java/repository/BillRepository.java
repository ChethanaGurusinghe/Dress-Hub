package repository;

import model.dto.Bill;
import java.sql.SQLException;

public interface BillRepository {

    boolean saveBill(Bill bill) throws SQLException;
    String generateInvoiceNo() throws SQLException;
}
