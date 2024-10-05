package controllers;

import models.User;
import models.InvitationCode;
import models.Role;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.List;

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
        String username = inviteUsernameField.getText();
        String roleName = inviteRoleField.getText();

        if (username.isEmpty() || roleName.isEmpty()) {
            return;  // Add some validation feedback here
        }

        Role role = new Role(roleName);
        InvitationCode code = new InvitationCode(generateCode(), role, LocalDateTime.now().plusDays(1));  // 1 day expiration

        System.out.println("Invitation Code: " + code.getCode());  // Simulate sending the code to the user
        
        // Store the user (you'll need a service to save this)
        User newUser = new User(username, null);  // No password yet, user will create it
        newUser.addRole(role);
        users.add(newUser);
        updateUserList();
    }

    private void updateUserList() {
        userListView.getItems().clear();
        for (User user : users) {
            userListView.getItems().add(user.getUsername() + " - Roles: " + user.getRoles());
        }
    }

    private String generateCode() {
        return "ABC123";  // Ideally this would generate a random code
    }

    // Additional methods for resetting accounts, deleting users, etc., would go here
}
