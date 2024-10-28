package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This app is in phase 1.
 * 
 * For phase 1, there is a simple login system to allow users to create an account
 * and choose roles. Admin role allows multiple privileges, mainly invitation of other users.
 * All other roles are currently unable to do anything besides logout.
 * 
 * @version 1.9.0
 **/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));	//Start the app by opening the login screen
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();																//Show GUI after loading the scene
    }

    public static void main(String[] args) {
        launch(args);
    }
}
