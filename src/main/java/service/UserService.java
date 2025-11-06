package service;

import model.dto.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    User checkLogin(String username, String password);

    List<User> getUsersByRole(String employee) throws SQLException;

    boolean deleteUser(String userId);

    boolean addUser(User user);

    boolean updateUser(User user);
}
