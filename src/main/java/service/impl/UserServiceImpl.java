package service.impl;

import model.dto.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepo = new UserRepositoryImpl();
    private Connection connection;
    private String role;

    @Override
    public User checkLogin(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<User> getUsersByRole(String employee) throws SQLException {
        String sql = "SELECT * FROM user WHERE role=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, role);
        ResultSet rs = ps.executeQuery();

        List<User> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new User(
                    rs.getString("user_id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("phone"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role")
            ));
        }
        return list;
    }

    @Override
    public boolean deleteUser(String userId) {
        return false;
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }
}
