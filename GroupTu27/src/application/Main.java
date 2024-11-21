package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import services.UserService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

import services.UserService;

/**
 * This app is in phase 3.
 * 
 * For phase 3, the ability to search, display, and ask the SO team for assistance has been added.
 * The most important part of this phase is the encryption of sensitive data to prevent leaks.
 * Security and Privacy have been strongly reinforced.
 * 
 * @version 2.6.0
 **/

public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));	//Start the app by opening the login screen
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();																//Show GUI after loading the scene
        
    }

    public static void main(String[] args) throws SQLException{
    	System.out.println("java version: "+System.getProperty("java.version"));
    	System.out.println("javafx.version: " + System.getProperty("javafx.version"));
		UserService userService = new UserService();
		userService.PrintTables();
		
        launch(args);
    }
}
