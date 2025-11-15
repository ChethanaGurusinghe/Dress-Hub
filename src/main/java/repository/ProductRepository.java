package repository;

import model.dto.Product;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    boolean addProduct(Product product) throws SQLException;
    boolean updateProduct(Product product) throws SQLException;
    boolean deleteProduct(String productId) throws SQLException;
    List<Product> getAllProducts() throws SQLException;
    Product searchProduct(String productId) throws SQLException;
    Product getProductById(String productId) throws SQLException;
    boolean reduceStock(String productId, int qty) throws SQLException;
    boolean updateStock(String productId, int newQty) throws SQLException;
}
