package services;

import models.HelpArticle;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HelpArticleService {
    private static HelpArticleService instance;
    private List<HelpArticle> articles; // Store articles

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
