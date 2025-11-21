package repository.impl;

import db.DBConnection;
import model.dto.Category;
import repository.CategoryRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository{

        @Override
        public boolean add(Category c) throws SQLException {
            String sql = "INSERT INTO category (category_id, name, description) VALUES (?, ?, ?)";
            try (Connection con = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, c.getCategoryId());
                ps.setString(2, c.getName());
                ps.setString(3, c.getDescription());
                return ps.executeUpdate() > 0;
            }
        }

        @Override
        public boolean update(Category c) throws SQLException {
            String sql = "UPDATE category SET name=?, description=? WHERE category_id=?";
            try (Connection con = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, c.getName());
                ps.setString(2, c.getDescription());
                ps.setString(3, c.getCategoryId());
                return ps.executeUpdate() > 0;
            }
        }

        @Override
        public boolean delete(String id) throws SQLException {
            String sql = "DELETE FROM category WHERE category_id=?";
            try (Connection con = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, id);
                return ps.executeUpdate() > 0;
            }
        }

        @Override
        public Category search(String id) throws SQLException {
            String sql = "SELECT * FROM category WHERE category_id=?";
            try (Connection con = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new Category(rs.getString("category_id"),
                            rs.getString("name"),
                            rs.getString("description"));
                }
                return null;
            }
        }

        @Override
        public List<Category> getAll() throws SQLException {
            String sql = "SELECT * FROM category";
            List<Category> list = new ArrayList<>();
            try (Connection con = DBConnection.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Category(
                            rs.getString("category_id"),
                            rs.getString("name"),
                            rs.getString("description")));
                }
            }
            return list;
        }
}
