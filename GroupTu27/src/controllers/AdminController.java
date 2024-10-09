package controllers;

import models.User;
import models.InvitationCode;
import models.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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

    private List<User> users;  // A list of all users

    public AdminController(List<User> users) {
        this.users = users;
    }

    @FXML
    private void initialize() {
        updateUserList();
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
        users.add(newUser);		// After adding all of the information, add the user to the list
        updateUserList();		// Update the list to include the new user
    }

    private void updateUserList() {	// This function clears and re-adds users to allow for an updated list
        userListView.getItems().clear();
        for (User user : users) {
            userListView.getItems().add(user.getUsername() + " - Roles: " + user.getRoles());
        }
    }

    private String generateCode() {	// Generating a code, likely for an invitation
        return "ABC123";  // Ideally this would generate a random code
    }

    // Additional methods for resetting accounts, deleting users, etc., would go here
}
