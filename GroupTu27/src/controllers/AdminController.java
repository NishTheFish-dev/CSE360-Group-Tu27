package controllers;

import models.User;
import models.UserService;
import models.InvitationCode;
import models.Role;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The admin controller is used to give the admin page function.
 * 
 * At the moment, it allows inviting a user by assigning a username and role.
 **/

public class AdminController {

    @FXML
    private TextField inviteUsernameField;
    
    @FXML
    private TextField inviteRoleField;

    @FXML
    private ListView<String> userListView;  // List view to display user information

    private UserService userService; // Reference to the user service
    
    public AdminController() {
    	this.userService = UserService.getInstance(); // Get the singleton instance of UserService
    }

    @FXML
    private void initialize() {
        //updateUserList();
    }

    @FXML
    private void handleInviteUser() {
        String username = inviteUsernameField.getText();	// Choose a username to assign to the new user
        String roleName = inviteRoleField.getText();	// Choose a role to assign to the new user

        if (username.isEmpty() || roleName.isEmpty()) {	// If either field is empty, cancel and possibly send error msg
            return;  // Add some validation feedback here
        }

        Role role = new Role(roleName);	//assign the chosen role to a variable through the Role command
        InvitationCode code = new InvitationCode(generateCode(), role, LocalDateTime.now().plusDays(1));  // 1 day expiration

        System.out.println("Invitation Code: " + code.getCode());  // Simulate sending the code to the user
        
        // Store the user (you'll need a service to save this)
        User newUser = new User(username, null);  // No password yet, user will create it
        newUser.addRole(role);	// Assign the new user the specified role by admin
        userService.addUser(newUser); // After adding all of the information, add the user to the list
        //updateUserList();		// Update the list to include the new user
    }

    /* TEST LATER
    private void updateUserList() {	// This function clears and re-adds users to allow for an updated list
        userListView.getItems().clear();
        for (User user : users) {
            userListView.getItems().add(user.getUsername() + " - Roles: " + user.getRoles());
        }
    }
    */
    
    @FXML
    private void handleResetUserAccount() {
    	//TO-DO
    }
    
    @FXML
    private void handleDeleteUserAccount() {
    	//TO-DO
    }
    
    @FXML
    private void handleListUserAccounts() {
    	//TO-DO
    }
    
    @FXML
    private void handleManageRoles() {
    	//TO-DO
    }
    
    private String generateCode() {	// Generating a code, likely for an invitation
        return "ABC123";  // Ideally this would generate a random code
    }
    
    @FXML
    private void handleLogout() {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            // Get the current stage (from any component within the scene, e.g., usernameField)
            Stage stage = (Stage) inviteUsernameField.getScene().getWindow();

            // Set the new scene for the stage (the login scene)
            stage.setScene(new Scene(root));

            // Optionally, reset the stage title if needed
            stage.setTitle("Login");

            // Show the new login page
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
