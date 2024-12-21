package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Article;

import java.util.List;

public interface IServiceArticle extends Services<Article> {
    Article fetchById(int id);
    Article fetchByLabel(String label);
    List<Article> fetchAllAvailableArticles();
    void editArticleStock(Article article, int stock);
}