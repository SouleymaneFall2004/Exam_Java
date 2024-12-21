package org.example.demo.core.implementations;

import org.example.demo.core.Repositories;
import org.example.demo.core.RepositoriesDb;

import java.sql.*;

public abstract class RepositoriesDbImpl<T> implements Repositories<T>, RepositoriesDb<T> {
    protected Connection connection;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;

    @Override
    public void openConnection() {
        if (connection == null || preparedStatement == null) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ExamJava2025", "postgres", "sfall");
            } catch (SQLException e) {
                System.out.println("[Connection error!]");
            }
        }
    }

    @Override
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.out.println("[Error closing connection!]");
        }
    }

    @Override
    public void prepareStatement(String sql) {
        try {
            if (sql.toUpperCase().contains("INSERT")) {
                preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(sql);
            }
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
    }

    @Override
    public void executeQuery() {
        try {
            if (preparedStatement != null) {
                resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
    }

    @Override
    public void executeUpdate() {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
    }
}