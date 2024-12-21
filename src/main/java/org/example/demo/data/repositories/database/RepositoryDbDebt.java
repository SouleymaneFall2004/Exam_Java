package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.data.repositories.interfaces.IRepositoryDebt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbDebt extends RepositoriesDbImpl<Debt> implements IRepositoryDebt {
    RepositoryDbClient repositoryDbClient = new RepositoryDbClient();
    @Override
    public void insert(Debt object) {
        try {
            openConnection();
            prepareStatement(String.format("INSERT INTO public.\"Debt\" (date, \"amountTotal\", \"amountSent\", \"amountMissing\", client_id, status) VALUES ('%s', %d, %d, %d, %d, '%s') RETURNING id;", object.getDate(), object.getAmountTotal(), object.getAmountSent(), object.getAmountMissing(), object.getClient().getId(), object.getStatus()));
            executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt(1);
                object.setId(generatedId);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public List<Debt> selectAll() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Debt\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Debt findById(int id) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Debt\" WHERE id = %d;", id));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Debt> findAllUnpaidDebts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Debt\" WHERE \"amountMissing\" > 0 ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Debt> findAllUnpaidDebtsOfClient(Client client) {
        try {
            openConnection();
            prepareStatement(String.format("SELECT * FROM public.\"Debt\" WHERE \"amountMissing\" > 0 AND client_id = %d ORDER BY id ASC;", client.getId()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Debt> findAllPendingDebtsOfClient(Client client) {
        try {
            openConnection();
            prepareStatement(String.format("SELECT * FROM public.\"Debt\" WHERE status = 'PENDING' AND client_id = %d ORDER BY id ASC;", client.getId()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Debt> findAllCancelledDebtsOfClient(Client client) {
        try {
            openConnection();
            prepareStatement(String.format("SELECT * FROM public.\"Debt\" WHERE status = 'CANCELLED' AND client_id = %d ORDER BY id ASC;", client.getId()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Debt> findAllPaidDebts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Debt\" WHERE \"amountMissing\" = 0 ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Debt> findAllByStatus(Status status) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Debt\" WHERE status = '%s' ORDER BY id ASC;", status.toString()));
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public void updateDebtAmountSent(Debt debt, int amount) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Debt\" SET \"amountSent\" = %d WHERE id = %d;", amount, debt.getId()));
        executeUpdate();
    }

    @Override
    public void updateDebtAmountMissing(Debt debt, int amount) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Debt\" SET \"amountMissing\" = %d WHERE id = %d;", amount, debt.getId()));
        executeUpdate();
    }

    @Override
    public void updateStatus(Debt debt, Status status) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Debt\" SET status = '%s' WHERE id = %d;", status.toString(), debt.getId()));
        executeUpdate();
    }

    @Override
    public List<Debt> collectorList() throws SQLException {
        List<Debt> debts = new ArrayList<>();
        while (resultSet.next()) {
            debts.add(new Debt(
                resultSet.getInt("id"),
                resultSet.getString("date"),
                resultSet.getInt("amountTotal"),
                resultSet.getInt("amountSent"),
                resultSet.getInt("amountMissing"),
                repositoryDbClient.findById(resultSet.getInt("client_id")),
                Status.getStatus(resultSet.getString("status")))
            );
        }
        return debts;
    }

    @Override
    public Debt collector() throws SQLException {
        resultSet.next();
        return new Debt(
            resultSet.getInt("id"),
            resultSet.getString("date"),
            resultSet.getInt("amountTotal"),
            resultSet.getInt("amountSent"),
            resultSet.getInt("amountMissing"),
            repositoryDbClient.findById(resultSet.getInt("client_id")),
            Status.getStatus(resultSet.getString("status"))
        );
    }
}