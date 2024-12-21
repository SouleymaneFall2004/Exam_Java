package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.State;
import org.example.demo.data.repositories.interfaces.IRepositoryAccount;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaAccount extends RepositoriesJpaImpl<Account> implements IRepositoryAccount {
    public RepositoryJpaAccount() {
        open();
    }

    @Override
    public List<Account> selectAll() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a", Account.class);
        return query.getResultList();
    }

    @Override
    public Account findById(int id) {
        return EM.find(Account.class, id);
    }

    @Override
    public Account findByLoginAndPassword(String login, String password) {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.login = :login AND a.password = :password", Account.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<Account> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Account> findAllEnabledAccounts() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.state = :state", Account.class);
        query.setParameter("state", State.ENABLED);
        return query.getResultList();
    }

    @Override
    public List<Account> findAllDisabledAccounts() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.state = :state", Account.class);
        query.setParameter("state", State.DISABLED);
        return query.getResultList();
    }

    @Override
    public List<Account> findAllClientAccounts() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.role = :role", Account.class);
        query.setParameter("role", Role.CLIENT);
        return query.getResultList();
    }

    @Override
    public List<Account> findAllShopkeeperAccounts() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.role = :role", Account.class);
        query.setParameter("role", Role.SHOPKEEPER);
        return query.getResultList();
    }

    @Override
    public List<Account> findAllAdminAccounts() {
        TypedQuery<Account> query = EM.createQuery("SELECT a FROM Account a WHERE a.role = :role", Account.class);
        query.setParameter("role", Role.ADMIN);
        return query.getResultList();
    }

    @Override
    public void updateState(Account account, State state) {
        try {
            EM.getTransaction().begin();
            Account managedAccount = EM.find(Account.class, account.getId());
            if (managedAccount != null) {
                managedAccount.setState(state);
            }
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        try {
            EM.getTransaction().begin();
            Account managedAccount = EM.find(Account.class, account.getId());
            if (managedAccount != null) {
                managedAccount.setClient(client);
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
