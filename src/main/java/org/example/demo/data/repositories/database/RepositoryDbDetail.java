package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;
import org.example.demo.data.repositories.interfaces.IRepositoryDetail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbDetail extends RepositoriesDbImpl<Detail> implements IRepositoryDetail {
    RepositoryDbArticle repositoryDbArticle = new RepositoryDbArticle();
    RepositoryDbDebt repositoryDbDebt = new RepositoryDbDebt();
    @Override
    public void insert(Detail object) {
        openConnection();
        prepareStatement(String.format("INSERT INTO public.\"Detail\" (stock, article_id, debt_id) VALUES (%d, %d, %d);", object.getStock(), object.getArticle().getId(), object.getDebt().getId()));
        executeUpdate();
    }

    @Override
    public List<Detail> selectAll() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Detail\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Detail> findDetailsFromDebt(Debt debt) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Detail\" WHERE debt_id = %d ORDER BY id ASC;", debt.getId()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Detail> collectorList() throws SQLException {
        List<Detail> details = new ArrayList<>();
        while (resultSet.next()) {
            details.add(new Detail(
                resultSet.getInt("id"),
                resultSet.getInt("stock"),
                repositoryDbArticle.findById(resultSet.getInt("article_id")),
                repositoryDbDebt.findById(resultSet.getInt("debt_id")))
            );
        }
        return details;
    }

    @Override
    public Detail collector() throws SQLException {
        resultSet.next();
        return new Detail(
            resultSet.getInt("id"),
            resultSet.getInt("stock"),
            repositoryDbArticle.findById(resultSet.getInt("article_id")),
            repositoryDbDebt.findById(resultSet.getInt("debt_id"))
        );
    }
}