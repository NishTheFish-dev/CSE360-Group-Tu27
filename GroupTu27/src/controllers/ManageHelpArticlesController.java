package controllers;

import database.BackupRestoreHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.HelpArticle;
import services.HelpArticleService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Manage Help Articles Controller handles the initialization and manipulation of the help articles.
 * 
 * It is capable of initializing, loading, adding, deleting, backing up, and restoring the articles.
 * It can also show errors and alerts
 */

public class ManageHelpArticlesController {

    @FXML private ListView<String> articleListView;
    @FXML private TextField headerField, titleField, keywordsField, referencesField, groupsField, backupFileNameField;
    @FXML private TextArea descriptionField, bodyField;
    @FXML private ChoiceBox<String> levelChoiceBox;

    private final HelpArticleService helpArticleService;
    private final BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper();
    private String selectedLevel;
    
    /**
     * Default Constructor
     */
    public ManageHelpArticlesController() {
        HelpArticleService tempService;
        try {
            tempService = new HelpArticleService();
        } catch (SQLException e) {
            e.printStackTrace();
            tempService = null; // Default to null if there’s an exception
        }
        this.helpArticleService = tempService;
    }
    
    /**
     * This function initializes the articles
     * 
     * @throws SQLException
     */
    @FXML
    public void initialize() throws SQLException {
        if (helpArticleService != null) {
        	try {
        		loadArticles(); // Load articles only if service was initialized successfully
        	}finally {
        	}
        
        }
        
    	levelChoiceBox.getItems().addAll("Beginner", "Intermediate", "Advanced", "Expert");
    	levelChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		System.out.print(newValue);
    		selectedLevel = newValue;

     		});
    	System.out.print(selectedLevel);
    }
    
    /**
     * This function loads the articles needed
     * 
     * @throws SQLException
     */
    private void loadArticles() throws SQLException{
    	List<String> displayArticles = new ArrayList<>();
    	List<HelpArticle> allArticles = helpArticleService.getAllArticles();
    	for(int i = 0; i < allArticles.size(); i++) {
    		displayArticles.add(allArticles.get(i).getId() + " " + allArticles.get(i).getTitle());
    	}
    	ObservableList<String> articles = FXCollections.observableArrayList(displayArticles);
    	articleListView.setItems(articles);
    	
    }

    /**
     * This function handles adding articles to the list
     * @throws SQLException
     */
    @FXML
    private void handleAddArticle() throws SQLException {
        try {
            // Parse the selected level from the choice box
        	
        	
        	

            // Create a new HelpArticle object with the updated constructor
            HelpArticle newArticle = new HelpArticle(
                    System.currentTimeMillis(),
                    headerField.getText(),
                    titleField.getText(),
                    descriptionField.getText(),
                    Arrays.asList(keywordsField.getText().split(",")), // Split keywords into a list
                    bodyField.getText(),
                    Arrays.asList(referencesField.getText().split(",")), // Split references into a list
                    Arrays.asList(groupsField.getText().split(",")), // Split groups into a list
                    selectedLevel // Set the selected level
            );

            // Add the article using the service
            helpArticleService.addArticle(newArticle);

            // Refresh the article list
            loadArticles();
        } catch (IllegalArgumentException e) {
            // Handle invalid ArticleLevel
            showError("Invalid level selected. Please choose a valid article level.");
        } catch (SQLException e) {
            // Handle SQL errors
            showError("An error occurred while adding the article. Please try again.");
            e.printStackTrace();
        }
    }


    private void showError(String string) {
		// TODO Auto-generated method stub		
	}

	/**
     * This functions handles deleting articles from the list
	 * @throws SQLException 
     */
    @FXML
    private void handleDeleteArticle() throws SQLException {
        String selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        long id = Long.parseLong(selectedArticle.substring(0,13));
        System.out.print(id);
        HelpArticle deleteArticle = null;
        List<HelpArticle> allArticles = helpArticleService.getAllArticles();
        for(int i = 0; i < allArticles.size(); i++) {
        	if(id == allArticles.get(i).getId()) {
        		deleteArticle = allArticles.get(i);
        	}
        }
        if (selectedArticle != null) {
            helpArticleService.removeArticle(deleteArticle);
            loadArticles();
        }
    }

    /**
     * This function handles backing-up the article list
     */
    @FXML
    private void handleBackupArticles() {
        String fileName = backupFileNameField.getText();
        try {
            backupRestoreHelper.backupDatabase(fileName);
            showAlert("Backup Successful", "Articles have been backed up to " + fileName);
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to backup articles.");
        }
    }

    /**
     * This function handles restoring a backup to the article list
     */
    @FXML
    private void handleRestoreArticles() {
        String fileName = backupFileNameField.getText();
        try {
            backupRestoreHelper.restoreDatabase(fileName);
            loadArticles(); // Refresh the articles
            showAlert("Restore Successful", "Articles have been restored from " + fileName);
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to restore articles.");
        }
    }

    /**
     * This function is a thrown visualization of a caught error
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
