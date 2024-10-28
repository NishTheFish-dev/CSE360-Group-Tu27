package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;
import models.PasswordReset;
import models.User;

/**
 * 
 * This handles the list of users during the current instance. The list is reset upon app reset.
 * 
 * Handled variables are userservice and the list of users
 * 
 **/

public class UserService {
    private static UserService instance;
    private List<User> users;
    private List<String> codes;
    private User currentUser;
    private int currentIndex;
    //private String curCode;
    private final DatabaseHelper dbHelper;

    public UserService() throws SQLException {
        this.dbHelper = new DatabaseHelper();
        this.users = dbHelper.getAllUsers(); // Load users from database
    }

    public void addUser(User user) throws SQLException {
        users.add(user);
        dbHelper.insertUser(user); // Save new user to database
    }

    public List<User> getUsers() {
        return users;
    }
    
    public void clearCode() {
    	codes.clear();
    }

    public void saveUser(User user) {
    	currentUser = user;
    	
    }

    public void removeUser(User user) {
    	User tempUser = user;
    	for(int i = 0; i < users.size(); i++) {
    		if(users.get(i).getEmail() == tempUser.getEmail() && users.get(i).getUsername() == tempUser.getUsername() && users.get(i).getPassword() == tempUser.getPassword()) {
    			users.remove(i);
    		}
    	}
    }
    public User getCurrent() {
    	return currentUser;
    }
    
    public void addCode(String code) {
    	codes.add(code);
    }
    public String getCode() {
    	return codes.get(0);
    }
    
    public void setPasswordReset(User user, PasswordReset passwordReset) {
        user.setPasswordReset(passwordReset);
    }

    // This function finds a user by username 
    public User findUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
}
