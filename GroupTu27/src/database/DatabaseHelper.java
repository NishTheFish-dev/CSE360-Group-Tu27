package database;

import models.HelpArticle;
import models.User;
import services.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

	
	private UserService userService;

	private static final String DB_URL = "jdbc:h2:./database"; // H2 database URL
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "";

    private Connection connection;

    // Constructor to establish database connection
    public DatabaseHelper() throws SQLException {
        connect();
        createTables(); // Ensure tables are created on the first run
    }

    // Connect to the H2 database
    private void connect() throws SQLException {
        try {
            Class.forName("org.h2.Driver"); // Explicitly load the H2 driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        }
    }

    // Close the database connection
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // Create tables for users and help articles if they don’t already exist
    private void createTables() throws SQLException {
        String createUserTableSQL = "CREATE TABLE IF NOT EXISTS Users (" +
                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                    "username VARCHAR(255) UNIQUE, " +
                                    "password VARCHAR(255), " +
                                    "role VARCHAR(50), " +
                                    "isAccountComplete INTEGER)";

        String createHelpArticleTableSQL = "CREATE TABLE IF NOT EXISTS HelpArticles (" +
                                           "id BIGINT PRIMARY KEY, " +
                                           "header VARCHAR(255), " +
                                           "title VARCHAR(255), " +
                                           "description VARCHAR(255), " +
                                           "keywords VARCHAR(255), " +
                                           "body TEXT, " +
                                           "references VARCHAR(255), " +
                                           "groups VARCHAR(255)," +
                                           "levels VARCHAR(255))";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUserTableSQL);
            stmt.execute(createHelpArticleTableSQL);
        }
    }

    // Insert a new user into the Users table
    public void insertUser(User user) throws SQLException {
        String insertUserSQL = "INSERT INTO Users (username, password, role, isAccountComplete) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSQL)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRoles().isEmpty() ? null : user.getRoles().get(0).getRoleName());
            pstmt.setInt(4, 1);
            pstmt.executeUpdate();
        }
    }

    // Insert a new help article into the HelpArticles table
    public void insertHelpArticle(HelpArticle article) throws SQLException {
        String insertArticleSQL = "INSERT INTO HelpArticles (id, header, title, description, keywords, body, references, groups, levels) " +
                                  "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertArticleSQL)) {
            pstmt.setLong(1, article.getId());
            pstmt.setString(2, article.getHeader());
            pstmt.setString(3, article.getTitle());
            pstmt.setString(4, article.getDescription());
            pstmt.setString(5, String.join(",", article.getKeywords())); // Join keywords as a comma-separated string
            pstmt.setString(6, article.getBody());
            pstmt.setString(7, String.join(",", article.getReferences())); // Join references as a comma-separated string
            pstmt.setString(8, String.join(",", article.getGroups())); // Join groups as a comma-separated string
            pstmt.setString(9,  article.getLevel());
            pstmt.executeUpdate();
        }
    }
    
    public void deleteUser(User user) throws SQLException {
    	String sql = "DELETE * FROM Users WHERE username="+user.getUsername();
    	Statement stmt = connection.createStatement();
		stmt.executeQuery(sql); 
    }
    
    public void deleteArticle(HelpArticle article) throws SQLException{
    	String sql = "DELETE * FROM HelpArticles WHERE id="+article.getId();
    	Statement stmt = connection.createStatement();
    	stmt.executeQuery(sql);
    }
    
    // Retrieve all users from the Users table
    public List<User> getAllUsers() throws SQLException {
        String query = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.addRole(new models.Role(rs.getString("role"))); // Assuming role is a single role for simplicity
                user.setAccountSetupComplete(rs.getInt("isAccountComplete"));
                users.add(user);
            }
        }
        return users;
    }
    
    public User getUser(String user) throws SQLException {
    	User tempUser = null;
    	String sql = "SELECT * FROM Users WHERE username="+user;
    	Statement stmt = connection.createStatement();
    	ResultSet rs = stmt.executeQuery(sql);
    	while(rs.next()) {
    		String username = rs.getString("username");
    		
    		tempUser = userService.findUserByUsername(username);
    		return tempUser;
    	}
		return tempUser;
    	
    }
    
    public int checkIfComplete(String username) throws SQLException {
    	String sql = "SELECT isAccountComplete FROM Users WHERE username="+username;
    	Statement stmt = connection.createStatement();
    	ResultSet rs = stmt.executeQuery(sql);
    	while(rs.next()) {
    		int complete = rs.getInt("isAccountComplete");
    		return complete;
    	}
    	return 0;
    }

    // Retrieve all help articles from the HelpArticles table
    public List<HelpArticle> getAllHelpArticles() throws SQLException {
        String query = "SELECT * FROM HelpArticles";
        List<HelpArticle> articles = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                HelpArticle article = new HelpArticle(
                    rs.getLong("id"),
                    rs.getString("header"),
                    rs.getString("title"),
                    rs.getString("description"),
                    List.of(rs.getString("keywords").split(",")), // Split keywords into a list
                    rs.getString("body"),
                    List.of(rs.getString("references").split(",")), // Split references into a list
                    List.of(rs.getString("groups").split(",")), // Split groups into a list
                    rs.getString("levels") // Set the level
                );
                articles.add(article);
            }
        }
        return articles;
    }
}
