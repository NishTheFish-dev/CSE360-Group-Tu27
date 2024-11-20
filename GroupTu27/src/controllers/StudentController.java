package controllers;

import models.HelpArticle;
import services.HelpArticleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StudentController {

    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> levelChoiceBox, groupChoiceBox;
    @FXML private ListView<String> searchResultsListView;
    @FXML private TextArea messageField;
    @FXML private Button sendGenericMessageButton, sendSpecificMessageButton, viewArticleButton, searchButton;

    private final HelpArticleService helpArticleService;
    private ObservableList<HelpArticle> allArticles;
    private ObservableList<String> currentGroups;
    private ObservableList<String> searchResults;

    public StudentController() {
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

        // Filter articles by search text, level, and group
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
    private void handleSendGenericMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            showAlert("Error", "Message cannot be empty.");
        } else {
            // Save or log the message
            System.out.println("Generic message sent: " + message);
            messageField.clear();
            showAlert("Success", "Your message has been sent to the system.");
        }
    }

    @FXML
    private void handleSendSpecificMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            showAlert("Error", "Message cannot be empty.");
        } else {
            // Save the message as a specific request
            System.out.println("Specific message sent: " + message);
            messageField.clear();
            showAlert("Success", "Your specific request has been sent to the system.");
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
