package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import controllers.AdminController;
import models.Role;
import services.UserService;

public class InviteUserController {

	@FXML
	private CheckBox AdminCheckBox, InstructorCheckBox, StudentCheckBox;
	@FXML
	private Text GeneratedCodeText;
	
	private UserService userService;
	
	
	public InviteUserController() throws SQLException {
    	this.userService = new UserService(); // Get the singleton instance of UserService
    }
	
	@FXML
    private void initialize() {
		
	}

	@FXML
	private void generateOTCButtonClicked() throws SQLException {
		Boolean Adminchecked = false;
		Boolean Instructorchecked = false;
		Boolean Studentchecked = false;
		
		String roles = "";
		
		if (AdminCheckBox.isSelected()) {
			Adminchecked = true;
			roles += "A";
			//System.out.println("admin selected");
		}
		if (InstructorCheckBox.isSelected()) {
			Instructorchecked = true;
			roles += "I";
			//System.out.println("instructor selected");
		}
		if (StudentCheckBox.isSelected()) {
			Studentchecked = true;
			roles += "S";
			//System.out.println("student selected");
		}
		
		if ((Adminchecked || Instructorchecked || Studentchecked) == false) {
			//System.out.println("please select at least one option");
		}
		else {
			String otp = AdminController.generateCode();
			
			GeneratedCodeText.setText("Your Code:\t" + otp);
			userService.addCode(otp, roles);
			
		}
	}
}
	
