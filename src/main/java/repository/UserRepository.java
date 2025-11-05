package repository;

import model.dto.User;

public interface UserRepository {

    User findByUsernameAndPassword(String username, String password);
}
