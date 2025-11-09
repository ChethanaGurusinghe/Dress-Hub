package service;

import model.dto.User;
import java.util.List;

public interface UserService {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(String userId);
    List<User> getAllUsers();
    List<User> searchUsers(String keyword);
    String generateNextUserId();
    String generatePassword(String fullName);
    String generateUsername(String fullName);
    User checkLogin(String username, String password);

}
