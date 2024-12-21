package org.example.demo.data.repositories.database;

import org.example.demo.core.implementations.RepositoriesDbImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.State;
import org.example.demo.data.repositories.interfaces.IRepositoryAccount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryDbAccount extends RepositoriesDbImpl<Account> implements IRepositoryAccount {
    RepositoryDbClient repositoryDbClient = new RepositoryDbClient();

    @Override
    public void insert(Account object) {
        try {
            openConnection();
            if (object.getClient() == null) {
                prepareStatement(String.format("INSERT INTO public.\"Account\" (login, password, role, state) VALUES ('%s', '%s', '%s', '%s') RETURNING id;", object.getLogin(), object.getPassword(), object.getRole(), object.getState()));
            } else {
                prepareStatement(String.format("INSERT INTO public.\"Account\" (login, password, role, state, client_id) VALUES ('%s', '%s', '%s', '%s', %d) RETURNING id;", object.getLogin(), object.getPassword(), object.getRole(), object.getState(), object.getClient().getId()));
            }
            executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int generatedId = resultSet.getInt("id");
                object.setId(generatedId);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public List<Account> selectAll() {
        try {
            openConnection();
            prepareStatement("Select * from public.\"Account\" ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Account findById(int id) {
        try {
            openConnection();
            prepareStatement(String.format("Select * from public.\"Account\" WHERE id = %d;", id));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public Account findByLoginAndPassword(String login, String password) {
        try {
            openConnection();
            prepareStatement(String.format("SELECT * FROM public.\"Account\" WHERE login = '%s' AND password = '%s';", login, password));
            executeQuery();
            return collector();
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Account> findAllEnabledAccounts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Account\" WHERE state = 'ENABLED' ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Account> findAllDisabledAccounts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Account\" WHERE state = 'DISABLED' ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Account> findAllClientAccounts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Account\" WHERE role = 'CLIENT' ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Account> findAllShopkeeperAccounts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Account\" WHERE role = 'SHOPKEEPER' ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public List<Account> findAllAdminAccounts() {
        try {
            openConnection();
            prepareStatement("SELECT * FROM public.\"Account\" WHERE role = 'ADMIN' ORDER BY id ASC;");
            executeQuery();
            return collectorList();
        } catch (SQLException e) {
            System.out.println("[Database operation failed!]");
        }
        return null;
    }

    @Override
    public void updateState(Account account, State state) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Account\" SET state = '%s' WHERE id = %d;", state.toString(), account.getId()));
        executeUpdate();
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        openConnection();
        prepareStatement(String.format("UPDATE public.\"Account\" SET client_id = %d WHERE id = %d;", client.getId(), account.getId()));
        executeUpdate();
    }

    @Override
    public List<Account> collectorList() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        while (resultSet.next()) {
            accounts.add(new Account(
                resultSet.getInt("id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                Role.getRole(resultSet.getString("role")),
                State.getState(resultSet.getString("state")),
                repositoryDbClient.findById(resultSet.getInt("client_id")))
            );
        }
        return accounts;
    }

    @Override
    public Account collector() throws SQLException {
        resultSet.next();
        return new Account(
            resultSet.getInt("id"),
            resultSet.getString("login"),
            resultSet.getString("password"),
            Role.valueOf(resultSet.getString("role")),
            State.valueOf(resultSet.getString("state")),
            repositoryDbClient.findById(resultSet.getInt("client_id"))
        );
    }
}