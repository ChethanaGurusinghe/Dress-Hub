package repository;

import model.dto.OrderDetail;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {

//    void saveOrderDetails(List<OrderDetail> orderDetails) throws SQLException;
    String generateOrderId() throws SQLException;
    boolean saveOrder(String orderId, double netTotal) throws SQLException;
    void saveOrderDetail(String orderId, String productId, int orderQty) throws SQLException;
}
