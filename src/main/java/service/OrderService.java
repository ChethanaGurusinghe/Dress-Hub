package service;

import model.dto.OrderDetail;
import model.dto.Product;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {

    // Get product details by ID
    Product getProductById(String productId) throws SQLException;

    // Place an order with order ID and cart list
    void placeOrder(String orderId, List<OrderDetail> cartList) throws SQLException;

    // Generate a new Order ID (O001, O002, etc.)
    String generateOrderId() throws SQLException;

    boolean isOrderExists(String orderId);
}
