package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;

import java.util.List;

public interface IRepositoryClient extends Repositories<Client> {
    Client findByPhone(String phone);

    Client findBySurname(String surname);
    Client findById(int id);
    List<Client> findAllWithAccount();
    List<Client> findAllWithoutAccount();
    void updateAccountOfClient(Account account, Client client);
}