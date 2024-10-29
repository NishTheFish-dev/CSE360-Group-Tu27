package controllers;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import models.HelpArticle;
import services.HelpArticleService;

public class HelpArticlesStudentViewController {

    @FXML private ListView<HelpArticle> articleListView;
    @FXML private Label titleLabel;
    @FXML private TextArea bodyTextArea;

    private final HelpArticleService helpArticleService;
    
    //Default Controller
    public HelpArticlesStudentViewController() {
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
     * This function initializes the articles and loads them
     */
    @FXML
    public void initialize() {
        if (helpArticleService != null) {
            loadArticles(); // Load articles only if service was initialized successfully
        }
    }

    /**
     * This function loads all articles to the list
     */
    private void loadArticles() {
        ObservableList<HelpArticle> articles = FXCollections.observableArrayList(helpArticleService.getAllArticles());
        articleListView.setItems(articles);
    }

    /**
     * This function handles the viewing of a specified article
     */
    @FXML
    private void handleViewArticle() {
        HelpArticle selectedArticle = articleListView.getSelectionModel().getSelectedItem();
        if (selectedArticle != null) {
            titleLabel.setText("Title: " + selectedArticle.getTitle());
            bodyTextArea.setText(selectedArticle.getBody());
        }
    }
}
