package controllers;
import java.util.Random;
import java.util.stream.Collectors;
import models.User;
import models.UserService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    
    public AdminController() {
    	this.userService = UserService.getInstance(); // Get the singleton instance of UserService
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
    private void handleInviteUser() {
        //String username = inviteUsernameField.getText();	// Choose a username to assign to the new user
       // String roleName = inviteRoleField.getText();	// Choose a role to assign to the new user
        roleChoice.setVisible(false);
        roleLabel.setVisible(false);
    	String inviteCode = generateCode();
    	userService.clearCode();
       // if (username.isEmpty() || roleName.isEmpty()) {	// If either field is empty, cancel and possibly send error msg
        //    return;  // Add some validation feedback here
      //  }

     //   Role role = new Role(roleName);	//assign the chosen role to a variable through the Role command
      //  InvitationCode code = new InvitationCode(generateCode(), role, LocalDateTime.now().plusDays(1));  // 1 day expiration

      //  System.out.println("Invitation Code: " + code.getCode());  // Simulate sending the code to the user
        
        // Store the user (you'll need a service to save this)
      //  User newUser = new User(username, null);  // No password yet, user will create it
       // newUser.addRole(role);	// Assign the new user the specified role by admin
       // userService.addUser(newUser); // After adding all of the information, add the user to the list
        //updateUserList();		// Update the list to include the new user
        inviteCodeLabel.setVisible(true);
        inviteCodeLabel.setText("Random Code: " + inviteCode);
        userService.addCode(inviteCode);
    }

    /* TEST LATER
    private void updateUserList() {	// This function clears and re-adds users to allow for an updated list
        userListView.getItems().clear();
        for (User user : users) {
            userListView.getItems().add(user.getUsername() + " - Roles: " + user.getRoles());
        }
    }
    */
    
    // This function will open the page used for resetting user accounts
    @FXML
    private void handleResetUserAccount() {		// Function used for the "Reset User Account" button
    	//TO-DO
    }
    
    // This function will open the page used for deleting user accounts
    @FXML
    private void handleDeleteUserAccount() {	// Function used for the "Delete User Account" button
    	//TO-DO
    }
    
    // This function will open the page used for listing all users
    @FXML
    private void handleListUserAccounts() {		// Function used for the "List User Accounts" button
    	//TO-DO
    	if(userListView.isVisible() == true) {
    		System.out.println(userListView.isVisible());
    		userListView.setVisible(false);
    		System.out.println("visible false");
    		listUserButton.setText("List User Accounts");
    	} else if(userListView.isVisible() == false) {
    		userListView.setVisible(true);
    		listUserButton.setText("Remove User List");
    		System.out.println(userListView.isVisible());
    	}
    	
    	inviteCodeLabel.setVisible(false);
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
    	roleChoice.getItems().clear();
    	removeOrAddRole.getItems().clear();
    	userSelectRole.getItems().clear();
    	if(roleChoice.isVisible() == true || userSelectRole.isVisible() || removeOrAddRole.isVisible()) {
    		roleChoice.setVisible(false);
    		roleButton.setText("Select Roles");
    	} else {
    		userSelectRole.setVisible(true);
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
    	roleChoice.getItems().addAll("Admin", "Student", "Teacher");
    	removeOrAddRole.getItems().addAll("Add", "Remove");
    	if(roleChoice.isVisible()) {
    		roleChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    			if(!currentUser.hasRole(new Role(newValue))){ //Looks at the ChoiceBox and checks the current value against the newValue
    				roleLabel.setVisible(true); //Sets the roleLabel to visible and adds text to it for selected role
    				roleLabel.setText("Selected: " + newValue);
    				currentUser.addRole(new Role(newValue)); //Gives Current User selected role in the choiceBox
    			} else {
    				roleLabel.setVisible(true); //Sets the label to tell admin that user already has selected role
    				roleLabel.setText("User already has that role!");
    			}
    		});
    	} else if(userSelectRole.isVisible()) {
    		userSelectRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    			currentUser = userService.findUserByUsername(newValue);
    			userSelectRole.setVisible(false);
    			removeOrAddRole.setVisible(true);
    		});
    	} else if(removeOrAddRole.isVisible()) {
    		removeOrAddRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue == "Remove") {
    				setRoleType = false;
    				roleChoice.setVisible(true);
    				removeOrAddRole.setVisible(false);
    			} else if(newValue == "Add"){
    				setRoleType = true;
    				roleChoice.setVisible(true);
    				removeOrAddRole.setVisible(false);
    			}
    		});
    	}
    	roleLabel.setVisible(false);
    }
    
    
    private String generateCode() {	// Generating a code, likely for an invitation
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


}
