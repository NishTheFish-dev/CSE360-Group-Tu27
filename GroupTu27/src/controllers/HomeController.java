package controllers;
import java.util.Random;
import java.util.stream.Collectors;
import models.User;
import services.UserService;
import models.InvitationCode;
import models.Role;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;



public class HomeController {
	

	private User currentUser;

	private UserService userService;
	
	@FXML
	private Label welcomeLabel;
	
	public HomeController() throws SQLException {
		this.userService = new UserService(); // Get the singleton instance of UserService
    }

    @FXML
    private void initialize() {
        //updateUserList();
    	currentUser = userService.getCurrent();
    	//System.out.println(currentUser.getUsername());
    	//System.out.println(currentUser.getRoles());
    }
    
    @FXML
    private void handleLogout() {
    	try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();


            // Get the current stage (from any component within the scene, e.g., usernameField)
            Stage stage = (Stage) welcomeLabel.getScene().getWindow();

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