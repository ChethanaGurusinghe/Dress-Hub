package service.impl;

import model.dto.Category;
import repository.CategoryRepository;
import repository.impl.CategoryRepositoryImpl;
import service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService{

        CategoryRepository repo = new CategoryRepositoryImpl();

        @Override
        public boolean addCategory(Category category) throws SQLException {
            return repo.add(category);
        }

        @Override
        public boolean updateCategory(Category category) throws SQLException {
            return repo.update(category);
        }

        @Override
        public boolean deleteCategory(String id) throws SQLException {
            return repo.delete(id);
        }

        @Override
        public Category searchCategory(String id) throws SQLException {
            return repo.search(id);
        }

        @Override
        public List<Category> getAllCategories() throws SQLException {
            return repo.getAll();
        }

}
