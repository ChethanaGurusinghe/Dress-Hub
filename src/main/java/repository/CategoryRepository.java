package repository;

import model.dto.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryRepository {

        boolean add(Category category) throws SQLException;
        boolean update(Category category) throws SQLException;
        boolean delete(String id) throws SQLException;
        Category search(String id) throws SQLException;
        List<Category> getAll() throws SQLException;
}
