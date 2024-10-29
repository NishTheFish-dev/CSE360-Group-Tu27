package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import models.User;
import services.UserService;
import models.Role;

import java.io.IOException;
import java.sql.SQLException;

public class RoleSelectionController {

    @FXML
    private ListView<String> roleListView;

    private User currentUser;
    
    private ObservableList<String> roles;
    
    private UserService userService; // Reference to the user service

    public RoleSelectionController() throws SQLException {
    	this.userService = new UserService();
    	this.currentUser = userService.getCurrent();
        // Default constructor
    }

    @FXML
    public void initialize() {
    	System.out.println(currentUser.getRoles());
    	setUser(currentUser);
        // This method is called after the FXML file is loaded
    }

    public void setUser(User user) {
        this.currentUser = user;
        this.roles = FXCollections.observableArrayList();

        // Get roles from the user and populate the ListView
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }

        roleListView.setItems(roles);
    }

    @FXML
    private void onSelectRole() {
        String selectedRole = roleListView.getSelectionModel().getSelectedItem();
        
        if (selectedRole == null) {
            showAlert("No Role Selected", "Please select a role to proceed.");
            return;
        }

        // Navigate to the role's home page based on selection
        switch (selectedRole) {
            case "Admin":
                loadAdminHome();
                break;
            case "Student":
                loadStudentHome();
                break;
            case "Instructor":
                loadInstructorHome(); // Placeholder for instructor role
                break;
            default:
                showAlert("Invalid Role", "Selected role is not recognized.");
        }
    }

    @FXML
    private void onCancel() {
        // Close the current stage or return to the previous view
        Stage stage = (Stage) roleListView.getScene().getWindow();
        stage.close();
    }

    private void loadAdminHome() {
        // Logic to load the Admin home view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/adminDashboard.fxml"));
            Parent root = loader.load();

            // Show the Admin Dashboard in a new window
            Stage stage = new Stage();
            stage.setTitle("Admin Dashboard");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Role Selection window
            Stage currentStage = (Stage) roleListView.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            showAlert("Error", "Failed to load Admin Dashboard.");
            e.printStackTrace();
        }
    }

    private void loadStudentHome() {
        // Logic to load the Student home view
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent root = loader.load();

            // Show the Student Home in a new window
            Stage stage = new Stage();
            stage.setTitle("Student Home");
            stage.setScene(new Scene(root));
            stage.show();

            // Close the Role Selection window
            Stage currentStage = (Stage) roleListView.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            showAlert("Error", "Failed to load Student Home.");
            e.printStackTrace();
        }
    }

    private void loadInstructorHome() {
        // Placeholder: Logic to load the Instructor home view (if you have a separate view for Instructor)
        showAlert("Instructor Role", "Instructor role functionality is not yet implemented.");
    }

    // This function is the visualization of the caught and thrown errors
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
