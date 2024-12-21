package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Article;
import org.example.demo.data.repositories.interfaces.IRepositoryArticle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbArticle extends RepositoriesDbImpl<Article> implements IRepositoryArticle {
    @Override
    public void insert(Article object) {
        openConnection();
        prepareStatement(String.format("INSERT INTO public.\"Article\" (label, stock, price) VALUES ('%s', %d, %d);", object.getLabel(), object.getStock(), object.getPrice()));
        executeUpdate();
    }

    @Override
    public List<Article> selectAll() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Article\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Article findById(int id) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Article\" WHERE id = %d;", id));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Article findByLabel(String label) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Article\" WHERE label LIKE '%s';", label));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Article> findAllAvailableArticles() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Article\" WHERE stock > 0 ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public void updateArticleStock(Article article, int stock) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Article\" SET stock = %d WHERE id = %d;", stock, article.getId()));
        executeUpdate();
    }

    @Override
    public List<Article> collectorList() throws SQLException {
        List<Article> articles = new ArrayList<>();
        while (resultSet.next()) {
            articles.add(new Article(
                resultSet.getInt("id"),
                resultSet.getString("label"),
                resultSet.getInt("stock"),
                resultSet.getInt("price"))
            );
        }
        return articles;
    }

    @Override
    public Article collector() throws SQLException {
        resultSet.next();
        return new Article(
            resultSet.getInt("id"),
            resultSet.getString("label"),
            resultSet.getInt("stock"),
            resultSet.getInt("price")
        );
    }
}