package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserController {

    @FXML
    private Label welcomeLabel;

    @SuppressWarnings("unused")
	private String userFullName;

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
        welcomeLabel.setText("Welcome, " + userFullName + "!");
    }

    @FXML
    private void handleLogout() {
        // Return to the login page
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();  // Simulating a logout, you'd load the login scene here
    }
}
