package controllers;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import controllers.AdminController;
import models.Role;

public class InviteUserController {

	@FXML
	private CheckBox AdminCheckBox, InstructorCheckBox, StudentCheckBox;
	
	@FXML
    private void initialize() {
		
	}

	@FXML
	public void generateOTCButtonClicked() {
		Boolean Adminchecked = false;
		Boolean Instructorchecked = false;
		Boolean Studentchecked = false;
		
		if (AdminCheckBox.isSelected()) {
			Adminchecked = true;
			System.out.println("admin selected");
		}
		if (InstructorCheckBox.isSelected()) {
			Instructorchecked = true;
			System.out.println("instructor selected");
		}
		if (StudentCheckBox.isSelected()) {
			Studentchecked = true;
			System.out.println("student selected");
		}
		
		if ((Adminchecked || Instructorchecked || Studentchecked) == false) {
			System.out.println("please select at least one option");
		}
		else {
			String otp = AdminController.generateOneTimePassword();
			System.out.println(otp);
		}
		
		
	}
}
	
