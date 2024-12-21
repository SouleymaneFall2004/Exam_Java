package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Article;
import org.example.demo.data.repositories.interfaces.IRepositoryArticle;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaArticle extends RepositoriesJpaImpl<Article> implements IRepositoryArticle {
    public RepositoryJpaArticle() {
        open();
    }

    @Override
    public List<Article> selectAll() {
        open();
        TypedQuery<Article> query = EM.createQuery("SELECT a FROM Article a", Article.class);
        return query.getResultList();
    }

    @Override
    public Article findById(int id) {
        return EM.find(Article.class, id);
    }

    @Override
    public Article findByLabel(String label) {
        return EM.find(Article.class, label);
    }

    @Override
    public List<Article> findAllAvailableArticles() {
        TypedQuery<Article> query = EM.createQuery("SELECT a FROM Article a WHERE a.stock > 0", Article.class);
        return query.getResultList();
    }

    @Override
    public void updateArticleStock(Article article, int stock) {
        try {
            EM.getTransaction().begin();
            Article managedArticle = EM.find(Article.class, article.getId());
            if (managedArticle != null) {
                managedArticle.setStock(stock);
            }
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }
}
