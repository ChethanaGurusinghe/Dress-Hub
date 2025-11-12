package repository.impl;

import db.DBConnection;
import model.dto.Bill;
import repository.BillRepository;

import java.sql.*;

public class BillRepositoryImpl implements BillRepository {

    @Override
    public boolean saveBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bill (invoice_no, order_id, created_at) VALUES (?, ?, ?)";
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, bill.getInvoiceNo());
        ps.setString(2, bill.getOrderId());
        ps.setTimestamp(3, bill.getCreatedAt());

        System.out.println("Saving Bill: " + bill);
        return ps.executeUpdate() > 0;
    }

    @Override
    public String generateInvoiceNo() throws SQLException {
        Connection conn = DBConnection.getInstance().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT invoice_no FROM bill ORDER BY invoice_no DESC LIMIT 1");

        if (rs.next()) {
            String lastId = rs.getString(1);
            String numPart = lastId.replaceAll("[^0-9]", "");
            int num = 0;
            if (!numPart.isEmpty()) num = Integer.parseInt(numPart);
            return String.format("INV%04d", num + 1);
        } else {
            return "INV0001";
        }
    }
}
