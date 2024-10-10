package controllers;

import models.User;
import models.Role;
import models.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.List;

/**
 * The login controller is used to give the login page function.
 * 
 * At the moment, it's main functions is to handle procedures for logging in and entering
 * an invite code given by an admin.
 * 
 * This also handles navigation to appropriate pages
 **/

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private TextField invitationCodeField;

    @FXML
    private Label errorMessageLabel;

    private UserService userService; // Reference to the user service
    
    //Default Constructor
    public LoginController() {
        this.userService = UserService.getInstance(); // Get the singleton instance of UserService
    }
    

    // This function takes the information input to check for valid login
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        List<User> users = userService.getUsers(); // Get the list of users from the service

        if (users.isEmpty()) {
            // The system has no users; the first user must create an admin account
            if (validateAdminCreation(username, password)) {
                User newUser = new User(username, password);
                newUser.addRole(new Role("Admin"));
                userService.addUser(newUser); // Save the new user in the service
                System.out.print(users);
                errorMessageLabel.setText("Admin account created. Please log in again.");
                clearLoginForm();
                return;
            } else {
                errorMessageLabel.setText("Passwords do not match or are invalid.");
                return;
            }
        }

        User user = userService.findUserByUsername(username); // Use the service to find the user
        if (user != null && user.getPassword().equals(password)) {
            if (!user.isAccountSetupComplete()) {
                loadSetupAccountPage(user);
            } else {
                navigateToHomePage(user);
            }
        } else {
            errorMessageLabel.setText("Invalid username or password.");
        }
        
    }

    private boolean validateAdminCreation(String username, String password) {
        String confirmPassword = confirmPasswordField.getText();  // Confirm password field is filled out one time
        return password != null && !password.isEmpty() && password.equals(confirmPassword) && username != null && !username.isEmpty();
    }

    private void clearLoginForm() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        invitationCodeField.clear();
    }

    
    // This function compares the invitation code to find appropriate account
    public void handleInvitationCode() {	
        String code = invitationCodeField.getText();
        // Process the invitation code and navigate to account setup
    }


	// When setting up a new account, this function creates and loads the GUI
    private void loadSetupAccountPage(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/setupAccount.fxml"));	// Fetching settings for the GUI
            Parent root = loader.load();
            
            SetupAccountController controller = loader.getController();	// Loading up the new controller for setting up an account
            controller.setUser(user);
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();	// Show the completed scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	// If all goes well with logging in, this takes us to the home page
    private void navigateToHomePage(User user) {
        try {
            if (user.getRoles().size() == 1) {	// In the case somebody has more than 1 role, they need to decide which home page to navigate to
                loadRoleHomePage(user, user.getRoles().get(0));	// With only 1 role, find and load into the correct home page
            } else {	// If there is more than 1 role, we go to role selection
                // Load role selection page if user has multiple roles
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/roleSelection.fxml"));	// Loading the GUI settings for role selection
                Parent root = loader.load();
                
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();	// Show the loaded scene for role selection
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
	// In the case somebody has been assigned more than 1 role, this allows them to choose which home page to navigate to
    private void loadRoleHomePage(User user, Role role) {
        try {
        	if (user.hasRole(role)) {
        		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/adminDashboard.fxml" ));	// Loading GUI settings for the appropriate home page
                Parent root = loader.load();
                
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();	// Show the loaded scene
        	}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
