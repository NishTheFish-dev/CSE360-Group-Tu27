package controllers;

import models.User;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class ResetPasswordController {
    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private Label errorMessageLabel;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @FXML
    private void handleResetPassword() throws IOException {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmNewPasswordField.getText();

        if (newPassword.equals(confirmPassword)) {
            user.resetPassword(newPassword);
            errorMessageLabel.setText("Password successfully reset. Please log in with the new password.");
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            // Get the current stage (from any component within the scene, e.g., usernameField)
            Stage stage = (Stage) newPasswordField.getScene().getWindow();

            // Set the new scene for the stage (the login scene)
            stage.setScene(new Scene(root));

            // Optionally, reset the stage title if needed
            stage.setTitle("Login");

            // Show the new login page
            stage.show();
        } else {
            errorMessageLabel.setText("Passwords do not match.");
        }
    }
}
