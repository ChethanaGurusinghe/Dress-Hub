package repository;

import java.sql.SQLException;

public interface OrderRepository {
    String generateOrderId() throws SQLException;

    boolean saveOrder(String orderId, double netTotal) throws SQLException;

    void saveOrderDetail(String orderId, String productId, int orderQty) throws SQLException;

    boolean getOrderById(String orderId) throws SQLException;
}
