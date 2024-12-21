package org.example.demo.core;

import java.sql.SQLException;
import java.util.List;

public interface RepositoriesDb<T> {
    void openConnection();
    void closeConnection();
    void prepareStatement(String sql);
    void executeQuery();
    void executeUpdate();
    List<T> collectorList() throws SQLException;
    T collector() throws SQLException;
}
