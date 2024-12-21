package org.example.demo.services;

import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.repositories.interfaces.IRepositoryClient;
import org.example.demo.services.interfaces.IServiceClient;

import java.util.List;

public class ServiceClient implements IServiceClient {
    private final IRepositoryClient repositoryClient;

    public ServiceClient(IRepositoryClient repositoryClient) {
        this.repositoryClient = repositoryClient;
    }

    @Override
    public void save(Client client) {
        repositoryClient.insert(client);
    }

    @Override
    public List<Client> show() {
        return repositoryClient.selectAll();
    }

    @Override
    public Client fetchByPhone(String phone) {
        return repositoryClient.findByPhone(phone);
    }

    @Override
    public Client fetchBySurname(String surname) {
        return repositoryClient.findBySurname(surname);
    }

    @Override
    public Client fetchById(int id) {
        return null;
    }

    @Override
    public List<Client> fetchAllWithAccount() {
        return repositoryClient.findAllWithAccount();
    }

    @Override
    public List<Client> fetchAllWithoutAccount() {
        return repositoryClient.findAllWithoutAccount();
    }

    @Override
    public void editAccountOfClient(Account account, Client client) {
        repositoryClient.updateAccountOfClient(account, client);
    }
}