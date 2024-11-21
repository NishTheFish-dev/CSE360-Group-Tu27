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
    
    public void PrintTables() throws SQLException {
    	this.dbHelper.printUserTables();
        this.dbHelper.printArticleTables();
        this.dbHelper.printInviteCodeTables();
    }

    public void addUser(User user) throws SQLException {
        users.add(user);
        dbHelper.insertUser(user); // Save new user to database
    }

    public List<User> getUsers() {
        return users;	// Return all users from the list
    }
    
    public void clearCode() {
    	codes.clear();	// Clear any codes
    }

    public void saveUser(User user) {
    	currentUser = user;	// Save a user as the current target
    	
    }

    public void removeUser(User user) {
    	User tempUser = user;
    	for(int i = 0; i < users.size(); i++) {
    		if(users.get(i).getEmail() == tempUser.getEmail() && users.get(i).getUsername() == tempUser.getUsername() && users.get(i).getPassword() == tempUser.getPassword()) {
    			users.remove(i);	// Remove a user from the list
    		}
    	}
    }
    
    public void removeCode(int code) throws SQLException {
    	this.dbHelper.deleteCode(code);
    }
    
    public User getCurrent() {
    	return currentUser;	// Returns the current user
    }
    
    //add a code to the database
    public void addCode(String code, String roles) throws SQLException {
    	this.dbHelper.insertInviteCodes(code, roles); //adds code to database
    }
    
    public String getCode() {
    	return codes.get(0);	// Get the code
    }
    
    //return true if it exist, otherwise return false
    public int compareCode(String code) throws SQLException {
    	int id = this.dbHelper.cmpCode(code);
    	return id;
    }
    
    public String getRolesFromCode(String code) throws SQLException {
    	String roles = this.dbHelper.getRolefromCode(code);
    	return roles;
    }
    
    public void setPasswordReset(User user, PasswordReset passwordReset) {
        user.setPasswordReset(passwordReset);	// Resetting a user's password
    }

    // This function finds a user by username 
    public User findUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }
}
