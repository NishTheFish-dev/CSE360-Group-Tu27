package models;

import java.util.List;

public class SpecialAccessGroup {
    private String groupName;
    private List<HelpArticle> articles; // List of help articles
    private List<User> admins;          // Admins of the group
    private List<User> instructors;    // Instructors in the group
    private List<User> students;       // Students with access

    // Constructor
    public SpecialAccessGroup(String groupName) {
        this.groupName = groupName;
    }

    // Getters and Setters
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<HelpArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<HelpArticle> articles) {
        this.articles = articles;
    }

    public void addArticle(HelpArticle article) {
        this.articles.add(article);
    }

    public void removeArticle(HelpArticle article) {
        this.articles.remove(article);
    }

    // Add user to group
    public void addUser(User user, String role) {
        switch (role.toLowerCase()) {
            case "admin":
                admins.add(user);
                break;
            case "instructor":
                instructors.add(user);
                break;
            case "student":
                students.add(user);
                break;
        }
    }

    // Remove user from group
    public void removeUser(User user) {
        admins.remove(user);
        instructors.remove(user);
        students.remove(user);
    }
}
