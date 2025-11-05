package service.impl;

import model.dto.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepo = new UserRepositoryImpl();

    @Override
    public User checkLogin(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password);
    }
}
