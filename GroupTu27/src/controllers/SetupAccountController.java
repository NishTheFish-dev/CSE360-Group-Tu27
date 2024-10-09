package controllers;

import models.User;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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

    @FXML
    private void handleCompleteAccountSetup() {
        user.setFirstName(firstNameField.getText());
        user.setMiddleName(middleNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setPreferredFirstName(preferredFirstNameField.getText());
        user.setEmail(emailField.getText());
        user.setAccountSetupComplete(true);

        navigateToLoginPage();
    }

    private void navigateToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) firstNameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
