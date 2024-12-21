package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;
import org.example.demo.data.repositories.interfaces.IRepositoryPayment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbPayment extends RepositoriesDbImpl<Payment> implements IRepositoryPayment {
    @Override
    public void insert(Payment object) {
        openConnection();
        prepareStatement(String.format("INSERT INTO public.\"Payment\" (date, amount, debt_id) VALUES ('%s', %d, %d);", object.getDate(), object.getAmount(), object.getDebt().getId()));
        executeUpdate();
    }

    @Override
    public List<Payment> selectAll() {
        try {
            openConnection();
            prepareStatement("Select * from public.\"Payment\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Payment> findPaymentsFromDebt(Debt debt) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Payment\" WHERE debt_id = %d ORDER BY id ASC;", debt.getId()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Payment> collectorList() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        while (resultSet.next()) {
            payments.add(new Payment(
                resultSet.getInt("id"),
                resultSet.getString("date"),
                resultSet.getInt("amount"))
            );
        }
        return payments;
    }

    @Override
    public Payment collector() throws SQLException {
        resultSet.next();
        return new Payment(
            resultSet.getInt("id"),
            resultSet.getString("date"),
            resultSet.getInt("amount")
        );
    }
}