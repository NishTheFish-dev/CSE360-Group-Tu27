package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * The user controller is used to give the user account page function.
 * 
 * At the moment, all this page does is welcome the user and have a button to logout.
 * 
 **/

public class UserController {

    @FXML
    private Label welcomeLabel;

    @SuppressWarnings("unused")
	private String userFullName;

    // Takes the username and welcomes the user
    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
        welcomeLabel.setText("Welcome, " + userFullName + "!");
    }

    // Log the user out, returning to the login page
    @FXML
    private void handleLogout() {
        // Return to the login page
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();  // Simulating a logout, you'd load the login scene here
    }
}
