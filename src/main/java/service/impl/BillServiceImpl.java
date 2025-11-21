package service.impl;

import model.dto.Bill;
import repository.BillRepository;
import repository.impl.BillRepositoryImpl;
import service.BillService;

import java.sql.SQLException;

public class BillServiceImpl implements BillService {

    private final BillRepository repo = new BillRepositoryImpl();

    @Override
    public boolean createBill(Bill bill) throws SQLException {
        if (bill.getOrderId() == null || bill.getOrderId().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty!");
        }
        return repo.saveBill(bill);
    }

    @Override
    public String generateInvoiceNo() throws SQLException {
        return repo.generateInvoiceNo();
    }
}
