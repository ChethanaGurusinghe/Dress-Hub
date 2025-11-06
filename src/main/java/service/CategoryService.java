package service;

import model.dto.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

        boolean addCategory(Category category) throws SQLException;
        boolean updateCategory(Category category) throws SQLException;
        boolean deleteCategory(String id) throws SQLException;
        Category searchCategory(String id) throws SQLException;
        List<Category> getAllCategories() throws SQLException;


}
