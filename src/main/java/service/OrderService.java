package service;

import model.dto.OrderDetail;
import model.dto.Product;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    Product getProductById(String productId) throws SQLException;

    void placeOrder(String orderId, List<OrderDetail> cartList) throws SQLException;

    String generateOrderId() throws SQLException;

    boolean isOrderExists(String orderId) throws SQLException;

    void saveOrderDetail(String orderId, String productId, int orderQty) throws SQLException;

    boolean saveOrder(String orderId, double netTotal) throws SQLException;

    void reduceProductStock(String productId, int orderQty) throws SQLException;
}
