package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Article;
import org.example.demo.data.repositories.interfaces.IRepositoryArticle;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryArticle extends RepositoriesImpl<Article> implements IRepositoryArticle {
    @Override
    public Article findById(int id) {
        return list.stream().filter(article -> article.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Article findByLabel(String label) {
        return list.stream().filter(article -> article.getLabel().compareTo(label) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Article> findAllAvailableArticles() {
        return list.stream().filter(article -> article.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public void updateArticleStock(Article article, int stock) {
        article.setStock(stock);
    }
}