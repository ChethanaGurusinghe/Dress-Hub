package service;

import model.dto.Product;
import java.util.List;

public interface ProductService {
    boolean addProduct(Product product) throws Exception;
    boolean updateProduct(Product product) throws Exception;
    boolean deleteProduct(String productId) throws Exception;
    List<Product> getAllProducts() throws Exception;
    Product searchProduct(String productId) throws Exception;
}
