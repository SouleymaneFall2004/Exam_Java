package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.State;

import java.util.List;

public interface IServiceAccount extends Services<Account> {
    Account fetchById(int id);
    Account fetchByLoginAndPassword(String login, String password);
    List<Account> fetchAllEnabledAccounts();
    List<Account> fetchAllDisabledAccounts();
    List<Account> fetchAllClientAccounts();
    List<Account> fetchAllShopkeeperAccounts();
    List<Account> fetchAllAdminAccounts();
    void editState(Account account, State state);
    void editAccountOfClient(Account account, Client client);
}