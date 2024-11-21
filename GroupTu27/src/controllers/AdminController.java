package controllers;
import java.util.Random;
import java.util.stream.Collectors;
import models.User;
import services.UserService;
import models.InvitationCode;
import models.PasswordReset;
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

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

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
    
    @FXML
    private Label inviteCodeLabel;
    
    @FXML
    private Label roleLabel;
    
    @FXML
    private Button listUserButton;
    
    @FXML
    private Button resetUser;
    
    @FXML
    private Button InviteUser;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button confirmButton;
    
    @FXML
    private Button roleButton;
    
    @FXML
    private ChoiceBox<String> roleChoice;
    
    @FXML
    private ChoiceBox<String> removeOrAddRole;
    
    @FXML
    private ChoiceBox<String> userSelectRole;
    
    private UserService userService; // Reference to the user service
    
    private User currentUser;
    
    private List<User> UserList;
    
    private Boolean setRoleType;
    
    private User tempUser;
    
    public AdminController() throws SQLException {
    	this.userService = new UserService(); // Get the singleton instance of UserService
    }

    @FXML
    private void initialize() {
        //updateUserList();
    	UserList = userService.getUsers(); 
        if (!UserList.isEmpty()) { //Grabs the firstmade user to be current for now (First made user will always be admin)
            currentUser = UserList.get(0); 
        } else {
            // Handle the case where the list is empty, e.g.,
            System.out.println("No users found."); 
        }
    }

    @FXML
    private void handleManageHelpArticles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageHelpArticles.fxml"));
            Parent root = loader.load();

            // Open the ManageHelpArticles view in a new stage
            Stage stage = new Stage();
            stage.setTitle("Manage Help Articles");
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //Invite User button controls
    @FXML
    private void handleInviteUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InviteUser.fxml"));
        Parent root = loader.load();
        //InviteUserController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Invite User");
        stage.setScene(new Scene(root));
        stage.show();
    }
    
    // This function will open the page used for resetting user accounts
    @FXML
    private void handleResetPassword() {
        // Find the user to reset the password
        User selectedUser = userService.findUserByUsername(userListView.getSelectionModel().getSelectedItem());

        if (selectedUser == null) {
            showAlert("Error", "Please select a valid user to reset the password.");
            return;
        }

        // Generate a one-time password
        String oneTimePassword = generateOneTimePassword();

        // Set expiration time to 24 hours from now
        LocalDateTime expirationTime = LocalDateTime.now().plusHours(24);

        // Create a new PasswordReset object for the user
        PasswordReset passwordReset = new PasswordReset(oneTimePassword, expirationTime);
        userService.setPasswordReset(selectedUser, passwordReset);

        // Inform the admin of the one-time password
        showAlert("Password Reset", "One-time password for user " + selectedUser.getUsername() + ": " + oneTimePassword);
    }

    // Generate a random one-time password (can be improved as needed)
    private String generateOneTimePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    
    // This function will open the page used for deleting user accounts
    @FXML
    private void handleDeleteUserAccount() {	// Function used for the "Delete User Account" button
    	//TO-DO
    	
    	inviteCodeLabel.setVisible(false);
    	List<String> userNames = new ArrayList<>();
    	userNames = userService.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
    	ObservableList<String> users = FXCollections.observableArrayList(userNames);
    	System.out.println(userNames);
    	System.out.println(users);
    	userListView.setItems(users);
    	userListView.setVisible(true);
    	deleteButton.setVisible(false);
    	confirmButton.setVisible(true);
    	showAlert("How to Delete", "Please select an account and click confirm delete.");
    	
    }
    
    //This function gathers the information needed for deleting a user and send it to handleDeleteUserAccount()
    @FXML
    private void deleteUser() {
    	tempUser = userService.findUserByUsername(userListView.getSelectionModel().getSelectedItem());
    	System.out.println(tempUser);
    	userService.removeUser(tempUser);
		System.out.println(userService.getUsers());
		userListView.setVisible(false);
		confirmButton.setVisible(false);
		deleteButton.setVisible(true);
    }
    
    // This function will open the page used for listing all users
    @FXML
    private void handleListUserAccounts() {		// Function used for the "List User Accounts" button
    	//TO-DO
    	if(userListView.isVisible() == true) {	// If the list is currently visible, hide it
    		System.out.println(userListView.isVisible());
    		userListView.setVisible(false);
    		System.out.println("visible false");
    		listUserButton.setText("List User Accounts");
    	} else if(userListView.isVisible() == false) {	// If the list is not currently visible, show it
    		userListView.setVisible(true);
    		listUserButton.setText("Remove User List");
    		System.out.println(userListView.isVisible());
    	}
    	
    	inviteCodeLabel.setVisible(false);	// Hide the invite code, in case it is visible. This avoids visual collisions
    	
    	// These lines set up and show the list of users
    	List<String> userNames = new ArrayList<>();
    	userNames = userService.getUsers().stream().map(User::getUsername).collect(Collectors.toList());
    	ObservableList<String> users = FXCollections.observableArrayList(userNames);
    	System.out.println(userNames);
    	System.out.println(users);
    	userListView.setItems(users);
    }
    
    // This function will open the page used for managing the roles of users
    @FXML
    private void handleManageRoles() {			// Function used for the "Manage Roles" button
    	//TO-DO
    	// Resets all relevant variables
    	roleChoice.getItems().clear();
    	removeOrAddRole.getItems().clear();
    	userSelectRole.getItems().clear();
    	
    	if(roleChoice.isVisible() == true || userSelectRole.isVisible() == true || removeOrAddRole.isVisible() == true) {
    		//If any of the above windows are open, hide them all.
    		roleChoice.setVisible(false);
    		userSelectRole.setVisible(false);
    		removeOrAddRole.setVisible(false);
    		roleButton.setText("Select Roles");
    	} else if(roleChoice.isVisible() == false && userSelectRole.isVisible() == false && removeOrAddRole.isVisible() == false){
    		//If all of the above windows are closed, show userSelectRole.
    		userSelectRole.setVisible(true);
    		roleChoice.setVisible(false);
    		removeOrAddRole.setVisible(false);
    		roleButton.setText("Hide Role Select");
    	}
    	userSelectRole.getItems().addAll(userService.getUsers().stream().map(User::getUsername).collect(Collectors.toList()));
    	/**
    	 * Bit of a longer explanation for the line above, starts with getItems(), this grabs the items for the listView.
    	 * addAll just adds the list of info in the ()
    	 * userService.getUsers() grabs all the users in a list of object user
    	 * .stream allows you to do operations with getUsers from before. .map(User::getUsername) uses the User function getUsername
    	 * to get the Username of each User object in the original list, .collect is a method in stream which uses Collectors.toList()
    	 * to input each Username from each user object into a complete list of strings. Hope this helps!
    	 */
    	
    	inviteCodeLabel.setVisible(false);
    	removeOrAddRole.getItems().addAll("Add", "Remove");	//These are the options the user will have for the role action
    	roleChoice.getItems().addAll("Admin", "Student", "Teacher");	//These are the options the user will have for the role selection
    	//This shows the relevant buttons to make the choice for role
     	userSelectRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
     		currentUser = userService.findUserByUsername(newValue);
     		userSelectRole.setVisible(false);
     		removeOrAddRole.setVisible(true);
     		});
     	
     	//This shows the relevant buttons to make the choice of removal or addition
    	removeOrAddRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		System.out.println(newValue);
    		//If the user chooses remove, removes the chosen role from the target user
    		if(newValue.equals("Remove")) {
    			setRoleType = false;
    			removeOrAddRole.setVisible(false);
    			roleChoice.setVisible(true);
    			
    		//If the user chooses add, adds the chosen role from the target user
    		} else if(newValue.equals("Add")){
    			setRoleType = true;
    			removeOrAddRole.setVisible(false);
    			roleChoice.setVisible(true);
    			
    		}
    	});
    	
    	roleChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		if(setRoleType == false) {
    			if(currentUser.hasRole(new Role(newValue))){ //Looks at the ChoiceBox and checks the current value against the newValue
        			roleLabel.setVisible(true); //Sets the roleLabel to visible and adds text to it for selected role
        			roleLabel.setText("Removed: " + newValue);
        			currentUser.removeRole(new Role(newValue)); //Gives Current User selected role in the choiceBox
        			System.out.println(currentUser.getRoles());
        		} else {
        			roleLabel.setVisible(true); //Sets the label to tell admin that user already has selected role
        			roleLabel.setText("User doesn't have that role!");
        		}
    		} else if(setRoleType == true) {
    			if(!currentUser.hasRole(new Role(newValue))){ //Looks at the ChoiceBox and checks the current value against the newValue
        			roleLabel.setVisible(true); //Sets the roleLabel to visible and adds text to it for selected role
        			roleLabel.setText("Selected: " + newValue);
        			currentUser.addRole(new Role(newValue)); //Gives Current User selected role in the choiceBox
        			System.out.println(currentUser.getRoles());
        		} else {
        			roleLabel.setVisible(true); //Sets the label to tell admin that user already has selected role
        			roleLabel.setText("User already has that role!");
        		}
    		}
    	}); 
    	roleLabel.setVisible(false);
    }
    
    
    public static String generateCode() {	// Generating a code, likely for an invitation
    	int length = 6; //Code length will be 6, underneath is the string of characters it can take from
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random(); //Random variable
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) { //Gets random character and appends it to code
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();  // Ideally this would generate a random code
    }
    
    
    @FXML
    private void handleLogout() {
        try {
            // Load the login FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Parent root = loader.load();


            // Get the current stage (from any component within the scene, e.g., usernameField)
            Stage stage = (Stage) inviteCodeLabel.getScene().getWindow();

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
    
    /**
     * This function is the thrown visualization for when an error is caught.
     * @param title
     * @param content
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
