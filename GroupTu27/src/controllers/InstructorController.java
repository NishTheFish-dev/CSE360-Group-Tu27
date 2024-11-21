package controllers;

import database.BackupRestoreHelper;
import models.HelpArticle;
import services.HelpArticleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class InstructorController {

    @FXML private TextField searchField, groupNameField;
    @FXML private ChoiceBox<String> levelChoiceBox, groupChoiceBox;
    @FXML private ListView<String> searchResultsListView, groupListView;
    @FXML private TextArea messageField, groupDetailsField;
    @FXML private Button createGroupButton, deleteGroupButton, backupButton, restoreButton;
    @FXML private Button searchButton, viewArticleButton, createArticleButton, deleteArticleButton;

    private final HelpArticleService helpArticleService;
    private final BackupRestoreHelper backupRestoreHelper = new BackupRestoreHelper();
    private ObservableList<HelpArticle> allArticles;
    private ObservableList<String> currentGroups;
    private ObservableList<String> searchResults;

    public InstructorController() {
        HelpArticleService tempService;
        try {
            tempService = new HelpArticleService();
        } catch (SQLException e) {
            e.printStackTrace();
            tempService = null;
        }
        this.helpArticleService = tempService;
    }

    @FXML
    public void initialize() {
        if (helpArticleService != null) {
            loadArticles(); // Load articles on initialization
            setupFilters();
        }
    }

    private void loadArticles() {
        List<HelpArticle> articles = helpArticleService.getAllArticles();
        allArticles = FXCollections.observableArrayList(articles);
        searchResults = FXCollections.observableArrayList();
        searchResultsListView.setItems(searchResults);

        // Extract groups from articles
        currentGroups = FXCollections.observableArrayList(
            articles.stream()
                    .flatMap(article -> article.getGroups().stream())
                    .distinct()
                    .collect(Collectors.toList())
        );
        groupChoiceBox.setItems(currentGroups);
        groupChoiceBox.getItems().add(0, "All");
        groupChoiceBox.setValue("All");
        groupListView.setItems(currentGroups);
    }

    private void setupFilters() {
        // Populate levels
        levelChoiceBox.getItems().addAll("All", "Beginner", "Intermediate", "Advanced", "Expert");
        levelChoiceBox.setValue("All");
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        String selectedLevel = levelChoiceBox.getValue();
        String selectedGroup = groupChoiceBox.getValue();

        searchResults.clear();

        allArticles.stream()
                .filter(article -> (searchText.isEmpty() ||
                        article.getTitle().toLowerCase().contains(searchText) ||
                        article.getDescription().toLowerCase().contains(searchText) ||
                        article.getKeywords().stream().anyMatch(keyword -> keyword.toLowerCase().contains(searchText)))
                )
                .filter(article -> selectedLevel.equals("All") || article.getLevel().equalsIgnoreCase(selectedLevel))
                .filter(article -> selectedGroup.equals("All") || article.getGroups().contains(selectedGroup))
                .forEach(article -> searchResults.add(formatShortArticle(article)));
    }

    private String formatShortArticle(HelpArticle article) {
        return String.format("%s: %s (Level: %s) - %s",
                article.getId(), article.getTitle(), article.getLevel(), article.getDescription());
    }

    @FXML
    private void handleViewArticle() {
        String selectedItem = searchResultsListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            long selectedArticleId = Long.parseLong(selectedItem.split(":")[0]);
            HelpArticle article = allArticles.stream()
                    .filter(a -> a.getId() == selectedArticleId)
                    .findFirst()
                    .orElse(null);

            if (article != null) {
                displayFullArticle(article);
            }
        }
    }

    private void displayFullArticle(HelpArticle article) {
        Alert articleAlert = new Alert(Alert.AlertType.INFORMATION);
        articleAlert.setTitle("Article Details");
        articleAlert.setHeaderText(article.getTitle());
        articleAlert.setContentText(
                "Level: " + article.getLevel() + "\n\n" +
                "Description: " + article.getDescription() + "\n\n" +
                "Body:\n" + article.getBody()
        );
        articleAlert.showAndWait();
    }

    @FXML
    private void handleCreateGroup() {
        String groupName = groupNameField.getText().trim();
        if (groupName.isEmpty()) {
            showAlert("Error", "Group name cannot be empty.");
        } else if (currentGroups.contains(groupName)) {
            showAlert("Error", "Group already exists.");
        } else {
            currentGroups.add(groupName);
            showAlert("Success", "Group created: " + groupName);
            groupNameField.clear();
        }
    }

    @FXML
    private void handleDeleteGroup() {
        String selectedGroup = groupListView.getSelectionModel().getSelectedItem();
        if (selectedGroup != null && currentGroups.contains(selectedGroup)) {
            currentGroups.remove(selectedGroup);
            showAlert("Success", "Group deleted: " + selectedGroup);
        }
    }

    @FXML
    private void handleBackup() {
        try {
            backupRestoreHelper.backupDatabase("backup-" + System.currentTimeMillis() + ".db");
            showAlert("Backup Successful", "All data has been backed up.");
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to create backup.");
        }
    }

    @FXML
    private void handleRestore() {
        try {
            backupRestoreHelper.restoreDatabase("backup.db");
            loadArticles(); // Refresh the articles after restore
            showAlert("Restore Successful", "Data has been restored.");
        } catch (IOException | SQLException e) {
            showAlert("Error", "Failed to restore data.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
