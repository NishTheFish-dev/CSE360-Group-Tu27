package services;

import models.HelpArticle;
import models.SpecialAccessGroup;
import models.User;

import java.util.List;
import java.util.stream.Collectors;

public class SearchService {

    public List<HelpArticle> searchArticles(String query, String group, String level, User user, List<SpecialAccessGroup> groups) {
        // Filter articles based on query, group, level, and user access
        return groups.stream()
                .filter(g -> g.getGroupName().equalsIgnoreCase(group))
                .flatMap(g -> g.getArticles().stream())
                .filter(article -> article.getLevel().equalsIgnoreCase(level) &&
                                   article.getTitle().contains(query))
                .collect(Collectors.toList());
    }
}
