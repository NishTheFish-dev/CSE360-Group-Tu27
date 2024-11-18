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
 * This app is in phase 2.
 * 
 * For phase 2, a database has been added to store information about users and help articles.
 * There are also more functionalities for admin users for managing users, roles, and help articles.
 * Instructors and Admins can manage articles, Students are able to view them.
 * 
 * @version 2.1.0
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
