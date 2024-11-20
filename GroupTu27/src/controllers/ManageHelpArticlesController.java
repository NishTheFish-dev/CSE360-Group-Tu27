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

public class ManageHelpArticlesController {

    @FXML private ListView<HelpArticle> articleListView;
    @FXML private TextField headerField, titleField, keywordsField, referencesField, groupsField, backupFileNameField;
    @FXML private TextArea descriptionField, bodyField;
    @FXML private ChoiceBox<String> levelChoiceBox;

    private final HelpArticleService helpArticleService;
    private final BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper();
    private String selectedLevel;
    public ManageHelpArticlesController() {
    	//fix
        HelpArticleService tempService;
        try {
            tempService = new HelpArticleService();
        } catch (SQLException e) {
            e.printStackTrace();
            tempService = null; // Default to null if there’s an exception
        }
        this.helpArticleService = tempService;
    }
    
    @FXML
    public void initialize() {
        if (helpArticleService != null) {
            loadArticles(); // Load articles only if service was initialized successfully
        }
    }
    

    private void loadArticles() {
        ObservableList<HelpArticle> articles = FXCollections.observableArrayList(helpArticleService.getAllArticles());
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
        	
        	selectedLevel = null;
        	levelChoiceBox.getItems().addAll("All", "Beginner", "Intermediate", "Advanced", "Expert");
        	levelChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        		selectedLevel = newValue;

         		});

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
     */
    @FXML
    private void handleDeleteArticle() {
        HelpArticle selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            helpArticleService.removeArticle(selectedArticle);
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
