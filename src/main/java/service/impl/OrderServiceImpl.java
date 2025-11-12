package service.impl;

import model.dto.OrderDetail;
import model.dto.Product;
import repository.OrderRepository;
import repository.ProductRepository;
import repository.impl.OrderRepositoryImpl;
import repository.impl.ProductRepositoryImpl;
import service.OrderService;

import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepo = new ProductRepositoryImpl();
    private final OrderRepository orderRepo = new OrderRepositoryImpl();

    @Override
    public Product getProductById(String productId) throws SQLException {
        return productRepo.getProductById(productId);
    }

    @Override
    public void placeOrder(String orderId, List<OrderDetail> cartList) throws SQLException {

        // 1️⃣ Calculate net total
        double netTotal = 0.0;
        for (OrderDetail od : cartList) {
            Product p = productRepo.getProductById(od.getProductId());
            if (p != null) {
                netTotal += p.getUnitPrice() * od.getOrderQty();
            }
        }

        // 2️⃣ Save order in 'orders' table first (important for FK)
        boolean orderSaved = orderRepo.saveOrder(orderId, netTotal);
        if (!orderSaved) {
            throw new SQLException("Failed to save order with ID " + orderId);
        }

        // 3️⃣ Save each order detail in 'orderdetail' table
        for (OrderDetail od : cartList) {
            orderRepo.saveOrderDetail(orderId, od.getProductId(), od.getOrderQty());
        }
    }

    @Override
    public String generateOrderId() throws SQLException {
        return orderRepo.generateOrderId();
    }

    @Override
    public boolean isOrderExists(String orderId) {
        return orderRepo.getOrderById(orderId) != null;
    }
}
