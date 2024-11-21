package models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;
import services.UserService;

/**
 * 
 * This handles everything involving the user account, the details, and possible changes to the account
 * 
 * The variables handled are username, password, email, first name, middle name, last name, preferred 
 * first name, roles, and the completed account.
 * 
 **/

public class User {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private List<Role> roles;
    private boolean isAccountSetupComplete;
    private PasswordReset passwordReset;
    private final DatabaseHelper dbHelper = new DatabaseHelper();

    // This compiles the user details needed for logging in
    public User(String username, String password) throws SQLException{
        this.username = username;
        this.password = password;
        this.roles = new ArrayList<>();
        this.isAccountSetupComplete = false;
        
    }
    
    // Getters and Setters for user data
    public String getUsername() {
        return username;	// Get username
    }

    public void setUsername(String username) {
        this.username = username;	// Set username
    }

    public String getPassword() {
        return password;	// Get password
    }

    public void setPassword(String password) {
        this.password = password;	// Set password
    }

    public String getEmail() {
        return email;	// Get email
    }

    public void setEmail(String email) {
        this.email = email;	// Set email
    }

    public String getFirstName() {
        return firstName;	// Get first name
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;	// Set first name
    }

    public String getMiddleName() {
        return middleName;	// Get middle name
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;	// Set middle name
    }

    public String getLastName() {
        return lastName;	// Get last name
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;	// Set last name
    }

    public String getPreferredFirstName() {
        return preferredFirstName;	// Get preferred first name
    }

    public void setPreferredFirstName(String preferredFirstName) {
        this.preferredFirstName = preferredFirstName;	// Set preferred first name
    }

    public boolean isAccountSetupComplete() {
        return isAccountSetupComplete;	// Check whether or not if the account is set up
    }

    public void setAccountSetupComplete(int complete) throws SQLException {
        if(complete == 1) {
        	isAccountSetupComplete = true;
        }
    	
    }
    
    public PasswordReset getPasswordReset() {
        return passwordReset;	// Get whether or not a password is being reset
    }

    public void setPasswordReset(PasswordReset passwordReset) {
        this.passwordReset = passwordReset;	// Set whether or not a password is being reset
    }

    public boolean isPasswordResetValid() {
        return passwordReset != null && passwordReset.isValidForUse();	// Checking for password validity based on restrictions
    }

    public void resetPassword(String newPassword) {
        this.password = newPassword;
        passwordReset.setUsed(true); // Mark the one-time password as used
    }
 
    
    public List<Role> getRoles() {
        return roles;	// Get the list of roles of a user
    }

    public void addRole(Role role){
    	
        if (!roles.contains(role)) {
        	
            roles.add(role);	// Add a new role to the user
        }
    }
    
    public void updateRole(Role role, String username) throws SQLException{
    	int id = dbHelper.getUserId(username);
        if (!roles.contains(role)) {
        	dbHelper.updateRole(role.getRoleName(), username, id);
            roles.add(role);	// Add a new role to the user
        }
    }

    public void removeRole(Role role) {
        roles.remove(role);		// Remove a role from the user
    }

    public boolean hasRole(Role role) {
        return roles.contains(role);	// Check which roles a user has
    }

    public String getFullName() {
        return preferredFirstName != null ? preferredFirstName : firstName;	// Returns the concatenation of the first name and last name
    }
}
