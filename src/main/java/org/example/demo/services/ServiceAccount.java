package org.example.demo.services;

import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.State;
import org.example.demo.data.repositories.interfaces.IRepositoryAccount;
import org.example.demo.services.interfaces.IServiceAccount;

import java.util.List;

public class ServiceAccount implements IServiceAccount {
    private final IRepositoryAccount repositoryAccount;

    public ServiceAccount(IRepositoryAccount repositoryAccount) {
        this.repositoryAccount = repositoryAccount;
    }

    @Override
    public void save(Account account) {
        repositoryAccount.insert(account);
    }

    @Override
    public List<Account> show() {
        return repositoryAccount.selectAll();
    }

    @Override
    public Account fetchById(int id) {
        return repositoryAccount.findById(id);
    }

    @Override
    public Account fetchByLoginAndPassword(String login, String password) {
        return repositoryAccount.findByLoginAndPassword(login, password);
    }

    @Override
    public List<Account> fetchAllEnabledAccounts() {
        return repositoryAccount.findAllEnabledAccounts();
    }

    @Override
    public List<Account> fetchAllDisabledAccounts() {
        return repositoryAccount.findAllDisabledAccounts();
    }

    @Override
    public List<Account> fetchAllClientAccounts() {
        return repositoryAccount.findAllClientAccounts();
    }

    @Override
    public List<Account> fetchAllShopkeeperAccounts() {
        return repositoryAccount.findAllShopkeeperAccounts();
    }

    @Override
    public List<Account> fetchAllAdminAccounts() {
        return repositoryAccount.findAllAdminAccounts();
    }

    @Override
    public void editState(Account account, State state) {
        repositoryAccount.updateState(account, state);
    }

    @Override
    public void editAccountOfClient(Account account, Client client) {
        repositoryAccount.updateAccountOfClient(account, client);
    }
}