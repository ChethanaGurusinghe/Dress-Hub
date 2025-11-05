package service;

import model.dto.User;

public interface UserService {

    User checkLogin(String username, String password);
}
