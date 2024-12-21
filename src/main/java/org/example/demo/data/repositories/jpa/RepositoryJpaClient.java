package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.repositories.interfaces.IRepositoryClient;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaClient extends RepositoriesJpaImpl<Client> implements IRepositoryClient {
    private final RepositoryJpaAccount repositoryJpaAccount = new RepositoryJpaAccount();

    public RepositoryJpaClient() {
        open();
    }

    @Override
    public void insert(Client objet) {
        try {
            if (objet.getAccount() != null) {
                repositoryJpaAccount.insert(objet.getAccount());
                objet.setAccount(repositoryJpaAccount.findById(objet.getAccount().getId()));
            }
            super.insert(objet);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public List<Client> selectAll() {
        TypedQuery<Client> query = EM.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    @Override
    public Client findByPhone(String phone) {
        TypedQuery<Client> query = EM.createQuery("SELECT c FROM Client c WHERE c.phone = :phone", Client.class);
        query.setParameter("phone", phone);
        List<Client> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Client findBySurname(String surname) {
        TypedQuery<Client> query = EM.createQuery("SELECT c FROM Client c WHERE c.surname = :surname", Client.class);
        query.setParameter("surname", surname);
        List<Client> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public Client findById(int id) {
        return EM.find(Client.class, id);
    }

    @Override
    public List<Client> findAllWithAccount() {
        TypedQuery<Client> query = EM.createQuery("SELECT c FROM Client c WHERE c.account IS NOT NULL", Client.class);
        return query.getResultList();
    }

    @Override
    public List<Client> findAllWithoutAccount() {
        TypedQuery<Client> query = EM.createQuery("SELECT c FROM Client c WHERE c.account IS NULL", Client.class);
        return query.getResultList();
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        try {
            EM.getTransaction().begin();
            Client managedClient = EM.find(Client.class, client.getId());
            if (managedClient != null) {
                managedClient.setAccount(account);
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
