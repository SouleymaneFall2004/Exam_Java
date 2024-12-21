package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.repositories.interfaces.IRepositoryClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbClient extends RepositoriesDbImpl<Client> implements IRepositoryClient {
    @Override
    public void insert(Client object) {
        try {
            openConnection();
            if (object.getAccount() == null) {
                prepareStatement(String.format("INSERT INTO public.\"Client\" (surname, phone, address) VALUES ('%s', '%s', '%s') RETURNING id;", object.getSurname(), object.getPhone(), object.getAddress()));
            } else {
                prepareStatement(String.format("INSERT INTO public.\"Client\" (surname, phone, address, account_id) VALUES ('%s', '%s', '%s', '%d') RETURNING id;", object.getSurname(), object.getPhone(), object.getAddress(), object.getAccount().getId()));
            }
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
    public List<Client> selectAll() {
        try {
            openConnection();
            prepareStatement("Select * from public.\"Client\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Client findByPhone(String phone) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Client\" WHERE phone = '%s';", phone));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Client findBySurname(String surname) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Client\" WHERE surname = '%s';", surname));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Client findById(int id) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Client\" WHERE id = %d;", id));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Client> findAllWithAccount() {
        try {
            openConnection();
            prepareStatement("Select * from public.\"Client\" WHERE account_id IS NOT NULL ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Client> findAllWithoutAccount() {
        try {
            openConnection();
            prepareStatement("Select * from public.\"Client\" WHERE account_id IS NULL ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Client\" SET account_id = %d WHERE id = %d;", account.getId(), client.getId()));
        executeUpdate();
    }

    @Override
    public List<Client> collectorList() throws SQLException {
        List<Client> clients = new ArrayList<>();
        while (resultSet.next()) {
            clients.add(new Client(
                resultSet.getInt("id"),
                resultSet.getString("surname"),
                resultSet.getString("phone"),
                resultSet.getString("address"))
            );
        }
        return clients;
    }

    @Override
    public Client collector() throws SQLException {
        resultSet.next();
        return new Client(
            resultSet.getInt("id"),
            resultSet.getString("surname"),
            resultSet.getString("phone"),
            resultSet.getString("address")
        );
    }
}