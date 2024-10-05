package controllers;

import models.User;
import models.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private TextField invitationCodeField;

    @FXML
    private Label errorMessageLabel;

    private List<User> users;  // Assuming user list is managed here (ideally would be in a service)

    public LoginController() {
    	//Default Constructor
    }
    
    public LoginController(List<User> users) {
        this.users = users;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = findUserByUsername(username);

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
    
    public void handleInvitationCode() {
        String code = invitationCodeField.getText();
        // Process the invitation code and navigate to account setup
    }

    private User findUserByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    private void loadSetupAccountPage(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/setupAccount.fxml"));
            Parent root = loader.load();
            
            SetupAccountController controller = loader.getController();
            controller.setUser(user);
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToHomePage(User user) {
        try {
            if (user.getRoles().size() == 1) {
                loadRoleHomePage(user, user.getRoles().get(0));
            } else {
                // Load role selection page if user has multiple roles
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/roleSelection.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadRoleHomePage(User user, Role role) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/views/" + role.getRoleName() + "Home.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
