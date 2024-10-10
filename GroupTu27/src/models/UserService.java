package models;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService instance;
    private List<User> users;

    private UserService() {
        users = new ArrayList<>(); // Initialize with an empty list of users
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User findUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
}
