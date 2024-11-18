package models;

import java.util.List;

public class HelpArticle {
    private long id; // Unique long integer identifier
    private String header; // Article header containing info like level and grouping
    private String title; // Title of the article
    private String description; // Short description/abstract
    private List<String> keywords; // Keywords for search
    private String body; // Body of the article
    private List<String> references; // Links to reference materials
    private List<String> groups; // Groups the article belongs to (e.g., "Eclipse", "H2")
    private ArticleLevel level;
    
    public enum ArticleLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED,
        EXPERT;
        
        /**
         * Compares the provided string to this enum constant name, ignoring case.
         *
         * @param levelName The name to compare.
         * @return True if the provided string matches this enum constant name (case-insensitive), false otherwise.
         */
        public boolean equalsIgnoreCase(String levelName) {
            return this.name().equalsIgnoreCase(levelName);
        }
    }

    public HelpArticle(long id, String header, String title, String description, List<String> keywords, String body, List<String> references, List<String> groups, ArticleLevel level) {
        this.id = id;
        this.header = header;
        this.title = title;
        this.description = description;
        this.keywords = keywords;
        this.body = body;
        this.references = references;
        this.groups = groups;
        this.level = level;
    }

    // Getters and Setters for each field
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }
    
    // Add method to check if the article belongs to a specific group
    public boolean belongsToGroup(String groupName) {
        return groups.contains(groupName);
    }
    
    public ArticleLevel getLevel() {
        return level;
    }

    public void setLevel(ArticleLevel level) {
        this.level = level;
    }
}
