package repository.impl;

import model.dto.OrderDetail;
import repository.OrderRepository;
import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

//    @Override
//    public void saveOrderDetails(List<OrderDetail> orderDetails) throws SQLException {
//        Connection con = DBConnection.getInstance().getConnection();
//        String sql = "INSERT INTO orderdetail(order_id, product_id, orderQty) VALUES (?,?,?)";
//        PreparedStatement pst = con.prepareStatement(sql);
//        for (OrderDetail od : orderDetails){
//            pst.setString(1, od.getOrderId());
//            pst.setString(2, od.getProductId());
//            pst.setInt(3, od.getOrderQty());
//            pst.addBatch();
//        }
//        pst.executeBatch();
//    }

    @Override
    public String generateOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT order_id FROM orderdetail ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            String lastId = rs.getString("order_id");
            int num = Integer.parseInt(lastId.substring(1)) + 1;
            return String.format("O%03d", num);
        }
        return "O001";
    }

    @Override
    public boolean saveOrder(String orderId, double netTotal) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO orders(order_id, net_total) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, orderId);
        pst.setDouble(2, netTotal);
        return pst.executeUpdate() > 0;
    }

    @Override
    public void saveOrderDetail(String orderId, String productId, int orderQty) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO orderdetail(order_id, product_id, orderQty) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, orderId);
        pst.setString(2, productId);
        pst.setInt(3, orderQty);
        pst.executeUpdate();
    }
}
