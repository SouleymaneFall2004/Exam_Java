package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.repositories.interfaces.IRepositoryClient;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryClient extends RepositoriesImpl<Client> implements IRepositoryClient {
    @Override
    public Client findByPhone(String phone) {
        return list.stream().filter(client -> client.getPhone().compareTo(phone) == 0).findFirst().orElse(null);
    }

    @Override
    public Client findById(int id) {
        return list.stream().filter(client -> client.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Client findBySurname(String surname) {
        return list.stream().filter(client -> client.getSurname().compareTo(surname) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Client> findAllWithAccount() {
        return list.stream().filter(client -> client.getAccount() != null).collect(Collectors.toList());
    }

    @Override
    public List<Client> findAllWithoutAccount() {
        return list.stream().filter(client -> client.getAccount() == null).collect(Collectors.toList());
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        client.setAccount(account);
    }
}