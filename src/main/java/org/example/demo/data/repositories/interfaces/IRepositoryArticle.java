package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Article;

import java.util.List;

public interface IRepositoryArticle extends Repositories<Article> {
    Article findById(int id);
    Article findByLabel(String label);
    List<Article> findAllAvailableArticles();
    void updateArticleStock(Article article, int stock);
}