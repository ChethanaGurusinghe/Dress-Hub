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

        double netTotal = 0;

        // 1. Calculate total
        for (OrderDetail od : cartList) {
            Product p = productRepo.getProductById(od.getProductId());
            if (p != null) {
                netTotal += p.getUnitPrice() * od.getOrderQty();
            }
        }

        // 2. Save main order
        if (!orderRepo.saveOrder(orderId, netTotal)) {
            throw new SQLException("Failed to save order with ID " + orderId);
        }

        // 3. Save order details
        for (OrderDetail od : cartList) {
            orderRepo.saveOrderDetail(orderId, od.getProductId(), od.getOrderQty());
        }

        // 4. Reduce product stock
        for (OrderDetail od : cartList) {
            reduceProductStock(od.getProductId(), od.getOrderQty());
        }
    }

    @Override
    public String generateOrderId() throws SQLException {
        return orderRepo.generateOrderId();
    }

    @Override
    public boolean isOrderExists(String orderId) throws SQLException {
        return orderRepo.getOrderById(orderId);
    }

    @Override
    public void saveOrderDetail(String orderId, String productId, int orderQty) throws SQLException {
        orderRepo.saveOrderDetail(orderId, productId, orderQty);
    }

    @Override
    public boolean saveOrder(String orderId, double netTotal) throws SQLException {
        return orderRepo.saveOrder(orderId, netTotal);
    }

    @Override
    public void reduceProductStock(String productId, int orderQty) throws SQLException {

        // Get current product
        Product product = productRepo.getProductById(productId);

        if (product == null) {
            throw new SQLException("Product not found: " + productId);
        }

        int currentStock = product.getQuantity();

        if (currentStock < orderQty) {
            throw new SQLException("Not enough stock for product " + productId);
        }

        int newQty = currentStock - orderQty;

        // Update database stock
        boolean updated = productRepo.updateStock(productId, newQty);

        if (!updated) {
            throw new SQLException("Failed to update stock for product " + productId);
        }
    }
}
