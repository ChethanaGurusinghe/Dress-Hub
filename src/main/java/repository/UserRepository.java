package repository;

import model.dto.User;
import java.util.List;

public interface UserRepository {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(String userId);
    List<User> getAllUsers();
    List<User> search(String keyword);
    String getLastUserId();
    User checkLogin(String username, String password);


}
