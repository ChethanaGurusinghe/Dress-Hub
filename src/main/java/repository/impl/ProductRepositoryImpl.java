package repository.impl;

import db.DBConnection;
import model.dto.Product;
import repository.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public boolean addProduct(Product product) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO product (product_id, description, unit_price, quantity, category_id, supplier_id) VALUES (?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, product.getProductId());
        pstm.setString(2, product.getDescription());
        pstm.setDouble(3, product.getUnitPrice());
        pstm.setInt(4, product.getQuantity());
        pstm.setString(5, product.getCategoryId());
        pstm.setString(6, product.getSupplierId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE product SET description=?, unit_price=?, quantity=?, category_id=?, supplier_id=? WHERE product_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, product.getDescription());
        pstm.setDouble(2, product.getUnitPrice());
        pstm.setInt(3, product.getQuantity());
        pstm.setString(4, product.getCategoryId());
        pstm.setString(5, product.getSupplierId());
        pstm.setString(6, product.getProductId());
        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteProduct(String productId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM product WHERE product_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, productId);
        return pstm.executeUpdate() > 0;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM product";
        ResultSet rs = connection.prepareStatement(sql).executeQuery();

        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Product(
                    rs.getString("product_id"),
                    rs.getString("description"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity"),
                    rs.getString("category_id"),
                    rs.getString("supplier_id")
            ));
        }
        return list;
    }

    @Override
    public Product searchProduct(String productId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM product WHERE product_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, productId);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return new Product(
                    rs.getString("product_id"),
                    rs.getString("description"),
                    rs.getDouble("unit_price"),
                    rs.getInt("quantity"),
                    rs.getString("category_id"),
                    rs.getString("supplier_id")
            );
        }
        return null;
    }

    @Override
    public Product getProductById(String productId) throws SQLException {
            Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM product WHERE product_id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, productId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getString("product_id"),
                        rs.getString("description"),
                        rs.getDouble("unit_price"),
                        rs.getInt("quantity"),
                        rs.getString("category_id"),
                        rs.getString("supplier_id")
                );
            }
            return null;
        }
    }


