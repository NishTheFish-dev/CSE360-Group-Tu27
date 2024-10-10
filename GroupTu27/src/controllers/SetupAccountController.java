package controllers;

import models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * The Setup Account controller is used to give the setup account page function.
 * 
 * This page simply takes the input on the setup account page and saves it to the new user.
 * It then navigates back to the login page
 * 
 **/

public class SetupAccountController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField preferredFirstNameField;

    @FXML
    private TextField emailField;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    // This function is used to handle completing account setup
    @FXML
    private void handleCompleteAccountSetup() {
        user.setFirstName(firstNameField.getText());	// Saving the first name from the text field
        user.setMiddleName(middleNameField.getText());	// Saving the middle name from the text field
        user.setLastName(lastNameField.getText());		// Saving the last name from the text field
        user.setPreferredFirstName(preferredFirstNameField.getText());	// Saving the preferred first name from the text field
        user.setEmail(emailField.getText());	// Saving the email from the text field
        user.setAccountSetupComplete(true);		// Marking the account as a completed setup so this page is avoided in the future

        navigateToLoginPage();	// Returning to the login page to officially sign in to the new account
    }

    // This function loads and shows the login page
    private void navigateToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));	// Loading the GUI settings for the login page
            Parent root = loader.load();

            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();	// Show the loaded scene
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
