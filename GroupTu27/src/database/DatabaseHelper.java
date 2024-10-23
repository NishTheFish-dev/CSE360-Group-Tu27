package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private static final String DB_URL = "jdbc:h2:./database/help_system"; // Path to the H2 database
    private static final String DB_USERNAME = "sa"; // Default username for H2
    private static final String DB_PASSWORD = ""; // No password for local H2 usage

    private Connection connection;

    // Initialize the database connection
    public DatabaseHelper() throws SQLException {
        connect();
    }

    // Establish the connection to the database
    private void connect() throws SQLException {
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

    // Create the HelpArticles table (if it doesn't already exist)
    public void createHelpArticlesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS HelpArticles (" +
                     "id BIGINT PRIMARY KEY, " +
                     "header VARCHAR(255), " +
                     "title VARCHAR(255), " +
                     "description VARCHAR(255), " +
                     "keywords VARCHAR(255), " +
                     "body TEXT, " +
                     "references VARCHAR(255), " +
                     "groups VARCHAR(255))";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        }
    }

    // Add a new help article to the database
    public void insertHelpArticle(long id, String header, String title, String description, String keywords, String body, String references, String groups) throws SQLException {
        String sql = "INSERT INTO HelpArticles (id, header, title, description, keywords, body, references, groups) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.setString(2, header);
            statement.setString(3, title);
            statement.setString(4, description);
            statement.setString(5, keywords);
            statement.setString(6, body);
            statement.setString(7, references);
            statement.setString(8, groups);
            statement.executeUpdate();
        }
    }

    // Retrieve all help articles from the database
    public List<String> getHelpArticles() throws SQLException {
        String sql = "SELECT * FROM HelpArticles";
        List<String> articles = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                articles.add(resultSet.getString("title")); // For demonstration, retrieving only titles
            }
        }
        return articles;
    }

    // Remove a help article by ID
    public void deleteHelpArticleById(long id) throws SQLException {
        String sql = "DELETE FROM HelpArticles WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
