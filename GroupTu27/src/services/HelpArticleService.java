package services;

import models.HelpArticle;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

import database.DatabaseHelper;


public class HelpArticleService {
    private static HelpArticleService instance;
    
    static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/360projectDB";  
	
	private final DatabaseHelper dbHelper;
    private List<HelpArticle> articles;

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt
	
	public HelpArticleService() throws SQLException {
        this.dbHelper = new DatabaseHelper();
        this.articles = dbHelper.getAllHelpArticles(); // Load articles from database
    }

    public void addArticle(HelpArticle article) throws SQLException {
        articles.add(article);
        dbHelper.insertHelpArticle(article); // Save new article to database
    }

    public List<HelpArticle> getAllArticles() {
        return articles;	// Returns all articles
    }

    public static HelpArticleService getInstance() throws SQLException {
        if (instance == null) {
            instance = new HelpArticleService();
        }
        return instance;	// Returns the current instance
    }

    public void removeArticle(HelpArticle article) {
        articles.remove(article);	// Remove an article
    }

    public void updateArticle(HelpArticle updatedArticle) {
        articles = articles.stream()
                .map(article -> article.getId() == updatedArticle.getId() ? updatedArticle : article)
                .collect(Collectors.toList());	// Update an article with the  new article
    }

    public List<HelpArticle> searchArticles(String keyword) {
        return articles.stream()
                .filter(article -> article.getKeywords().contains(keyword))
                .collect(Collectors.toList());	// Finds an article via keywords
    }

    public List<HelpArticle> listArticlesByGroup(String group) {
        return articles.stream()
                .filter(article -> article.belongsToGroup(group))
                .collect(Collectors.toList()); // List all articles, sorted by groups
    }
    
    /**
     * Backup all articles to external storage
     * @param fileName
     * @param includeGroups
     * @param groupNames
     * @throws IOException
     */
    public void backupArticles(String fileName, boolean includeGroups, List<String> groupNames) throws IOException {
        List<HelpArticle> articlesToBackup = articles;	

        if (includeGroups && groupNames != null) {
            articlesToBackup = articles.stream()
                    .filter(article -> article.getGroups().stream().anyMatch(groupNames::contains))
                    .collect(Collectors.toList());
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(articlesToBackup);
        }
    }

    /**
     * Restore the articles to the backup
     * @param fileName
     * @param removeExisting
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void restoreArticles(String fileName, boolean removeExisting) throws IOException, ClassNotFoundException {
        List<HelpArticle> restoredArticles;	

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            restoredArticles = (List<HelpArticle>) ois.readObject();
        }

        if (removeExisting) {
            articles.clear();
        }

        for (HelpArticle restored : restoredArticles) {
            if (articles.stream().noneMatch(article -> article.getId() == restored.getId())) {
                articles.add(restored);
            }
        }
    }

}
