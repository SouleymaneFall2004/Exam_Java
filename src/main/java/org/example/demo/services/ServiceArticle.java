package org.example.demo.services;

import org.example.demo.data.entities.Article;
import org.example.demo.data.repositories.interfaces.IRepositoryArticle;
import org.example.demo.services.interfaces.IServiceArticle;

import java.util.List;

public class ServiceArticle implements IServiceArticle {
    private final IRepositoryArticle repositoryArticle;

    public ServiceArticle(IRepositoryArticle repositoryArticle) {
        this.repositoryArticle = repositoryArticle;
    }

    @Override
    public void save(Article object) {
        repositoryArticle.insert(object);
    }

    @Override
    public List<Article> show() {
        return repositoryArticle.selectAll();
    }

    @Override
    public Article fetchById(int id) {
        return repositoryArticle.findById(id);
    }

    @Override
    public Article fetchByLabel(String label) {
        return repositoryArticle.findByLabel(label);
    }

    @Override
    public List<Article> fetchAllAvailableArticles() {
        return repositoryArticle.findAllAvailableArticles();
    }

    @Override
    public void editArticleStock(Article article, int stock) {
        repositoryArticle.updateArticleStock(article, stock);
    }
}