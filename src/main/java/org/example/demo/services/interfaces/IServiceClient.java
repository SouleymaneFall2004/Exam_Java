package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;

import java.util.List;

public interface IServiceClient extends Services<Client> {
    Client fetchByPhone(String object);
    Client fetchById(int id);
    Client fetchBySurname(String surname);
    List<Client> fetchAllWithAccount();
    List<Client> fetchAllWithoutAccount();
    void editAccountOfClient(Account account, Client client);
}