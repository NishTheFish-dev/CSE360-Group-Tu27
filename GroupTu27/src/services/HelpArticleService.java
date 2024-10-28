package services;

import models.HelpArticle;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;


public class HelpArticleService {
    private static HelpArticleService instance;
    private List<HelpArticle> articles; // Store articles
    static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:~/360projectDB";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 

	private Connection connection = null;
	private Statement statement = null; 
	//	PreparedStatement pstmt
	
	
	
	public void DatabaseHelper() throws Exception {
		
	}
	
	private void createTables() throws SQLException {
		
		String postTable = "CREATE TABLE IF NOT EXISTS helpArticles ("
				+ "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
				+ "header VARCHAR(255) UNIQUE, "
				+ "title VARCHAR(255), "
				+ "shortDesc VARCHAR(255), "
				+ "searchMaterial VARCHAR(255), "
				+ "body VARCHAR(255), "
				+ "referenceLinks VARCHAR(255), "
				+ "otherInfo VARCHAR(255))";
		statement.execute(postTable);
	}
	
	public void connectToDatabase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			statement = connection.createStatement(); 
			createTables();  // Create the necessary tables if they don't exist
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	
    private HelpArticleService() {
        this.articles = new ArrayList<>();
    }

    public static HelpArticleService getInstance() {
        if (instance == null) {
            instance = new HelpArticleService();
        }
        return instance;
    }

    public void addArticle(HelpArticle article) {
        articles.add(article);
    }

    public void removeArticle(HelpArticle article) {
        articles.remove(article);
    }

    public void updateArticle(HelpArticle updatedArticle) {
        articles = articles.stream()
                .map(article -> article.getId() == updatedArticle.getId() ? updatedArticle : article)
                .collect(Collectors.toList());
    }

    public List<HelpArticle> searchArticles(String keyword) {
        return articles.stream()
                .filter(article -> article.getKeywords().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<HelpArticle> listArticlesByGroup(String group) {
        return articles.stream()
                .filter(article -> article.belongsToGroup(group))
                .collect(Collectors.toList());
    }

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

    public List<HelpArticle> getAllArticles() {
        return articles;
    }
}
