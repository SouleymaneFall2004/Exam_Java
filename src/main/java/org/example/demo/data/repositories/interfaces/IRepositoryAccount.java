package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.State;

import java.util.List;

public interface IRepositoryAccount extends Repositories<Account> {
    Account findById(int id);
    Account findByLoginAndPassword(String login, String password);
    List<Account> findAllEnabledAccounts();
    List<Account> findAllDisabledAccounts();
    List<Account> findAllClientAccounts();
    List<Account> findAllShopkeeperAccounts();
    List<Account> findAllAdminAccounts();
    void updateState(Account account, State state);
    void updateAccountOfClient(Account account, Client client);
}