package service.impl;

import model.dto.Product;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;
import service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    ProductRepository repo = new ProductRepositoryImpl();

    @Override
    public boolean addProduct(Product product) throws Exception {
        return repo.addProduct(product);
    }

    @Override
    public boolean updateProduct(Product product) throws Exception {
        return repo.updateProduct(product);
    }

    @Override
    public boolean deleteProduct(String productId) throws Exception {
        return repo.deleteProduct(productId);
    }

    @Override
    public List<Product> getAllProducts() throws Exception {
        return repo.getAllProducts();
    }

    @Override
    public Product searchProduct(String productId) throws Exception {
        return repo.searchProduct(productId);
    }
}
