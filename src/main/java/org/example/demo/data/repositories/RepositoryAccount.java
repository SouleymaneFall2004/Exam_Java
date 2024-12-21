package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Account;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.State;
import org.example.demo.data.repositories.interfaces.IRepositoryAccount;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryAccount extends RepositoriesImpl<Account> implements IRepositoryAccount {
    @Override
    public Account findById(int id) {
        return list.stream().filter(account -> account.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Account findByLoginAndPassword(String login, String password) {
        return list.stream().filter(account -> account.getLogin().compareTo(login) == 0 && account.getPassword().compareTo(password) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Account> findAllEnabledAccounts() {
        return list.stream().filter(account -> account.getState() == State.ENABLED).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllDisabledAccounts() {
        return list.stream().filter(account -> account.getState() == State.DISABLED).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllClientAccounts() {
        return list.stream().filter(account -> account.getRole() == Role.CLIENT).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllShopkeeperAccounts() {
        return list.stream().filter(account -> account.getRole() == Role.SHOPKEEPER).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllAdminAccounts() {
        return list.stream().filter(account -> account.getRole() == Role.ADMIN).collect(Collectors.toList());
    }

    @Override
    public void updateState(Account account, State state) {
        account.setState(state);
    }

    @Override
    public void updateAccountOfClient(Account account, Client client) {
        account.setClient(client);
    }
}