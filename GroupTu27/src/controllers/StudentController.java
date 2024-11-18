package controllers;

import services.SearchService;
import models.HelpArticle;
import models.User;
import models.SpecialAccessGroup;

import java.util.List;

public class StudentController {
    private SearchService searchService = new SearchService();
    private User currentUser;

    // Constructor
    public StudentController(User currentUser) {
        this.currentUser = currentUser;
    }

    // Search functionality
    public void searchArticles(String query, String group, String level, List<SpecialAccessGroup> groups) {
        List<HelpArticle> results = searchService.searchArticles(query, group, level, currentUser, groups);
        System.out.println("Search Results: ");
        for (HelpArticle article : results) {
            System.out.println(article.getTitle());
        }
    }

    // Send message
    public void sendMessage(String message) {
        System.out.println("Message sent: " + message);
    }
}
