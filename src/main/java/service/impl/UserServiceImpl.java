package service.impl;


import model.dto.User;
import repository.UserRepository;
import repository.impl.UserRepositoryImpl;
import service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public boolean addUser(User user) {

        String generatedUsername = generateUsername(user.getFullName());
        String generatedPassword = generatePassword(user.getFullName());

        user.setUserName(generatedUsername);
        user.setPassword(generatedPassword);

        return userRepository.addUser(user);
    }

    @Override
    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        return userRepository.deleteUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllUsers();
        }

        List<User> allUsers = getAllUsers();
        keyword = keyword.toLowerCase();

        List<User> filteredUsers = new ArrayList<>();
        for (User u : allUsers) {
            if (u.getUserId().toLowerCase().contains(keyword) ||
                    u.getFullName().toLowerCase().contains(keyword) ||
                    u.getEmail().toLowerCase().contains(keyword) ||
                    u.getPhoneNo().toLowerCase().contains(keyword)) {
                filteredUsers.add(u);
            }
        }
        return filteredUsers;
    }

    private String randomAlphaNumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }


    @Override
    public String generateNextUserId() {
        String lastId = userRepository.getLastUserId();
        if (lastId != null) {
            int num = Integer.parseInt(lastId.replace("U", "")) + 1;
            return String.format("U%03d", num);
        } else {
            return "U001";
        }
    }

    @Override
    public String generatePassword(String fullName) {
        String[] names = fullName.split(" ");
        String firstName = names[0].toLowerCase();
        String lastInitial = names.length > 1 ? names[1].substring(0, 1).toUpperCase() : "X";


        String randomCode = randomAlphaNumeric(2);

        return firstName + "@" + lastInitial + randomCode;
    }

    @Override
    public String generateUsername(String fullName) {
        String[] names = fullName.split(" ");
        if (names.length < 2) {

            return names[0];
        }
        return names[0].toLowerCase() + names[1].substring(0, 1).toUpperCase();
    }

    @Override
    public User checkLogin(String username, String password) {
        return userRepository.checkLogin(username, password);
    }



}
