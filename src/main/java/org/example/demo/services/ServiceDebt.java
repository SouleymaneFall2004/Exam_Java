package org.example.demo.services;

import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.data.repositories.interfaces.IRepositoryDebt;
import org.example.demo.services.interfaces.IServiceDebt;

import java.util.List;

public class ServiceDebt implements IServiceDebt {
    private final IRepositoryDebt repositoryDebt;

    public ServiceDebt(IRepositoryDebt repositoryDebt) {
        this.repositoryDebt = repositoryDebt;
    }

    @Override
    public void save(Debt debt) {
        repositoryDebt.insert(debt);
    }

    @Override
    public List<Debt> show() {
        return repositoryDebt.selectAll();
    }

    @Override
    public Debt fetchById(int id) {
        return repositoryDebt.findById(id);
    }

    @Override
    public List<Debt> fetchAllUnpaidDebts() {
        return repositoryDebt.findAllUnpaidDebts();
    }

    @Override
    public List<Debt> fetchAllUnpaidDebtsOfClient(Client client) {
        return repositoryDebt.findAllUnpaidDebtsOfClient(client);
    }

    @Override
    public List<Debt> fetchAllPendingDebtsOfClient(Client client) {
        return repositoryDebt.findAllPendingDebtsOfClient(client);
    }

    @Override
    public List<Debt> fetchAllCancelledDebtsOfClient(Client client) {
        return repositoryDebt.findAllCancelledDebtsOfClient(client);
    }

    @Override
    public List<Debt> fetchAllPaidDebts() {
        return repositoryDebt.findAllPaidDebts();
    }

    @Override
    public List<Debt> fetchAllByStatus(Status status) {
        return repositoryDebt.findAllByStatus(status);
    }

    @Override
    public void editDebtAmountSent(Debt debt, int amount) {
        repositoryDebt.updateDebtAmountSent(debt, amount);
    }

    @Override
    public void editDebtAmountMissing(Debt debt, int amount) {
        repositoryDebt.updateDebtAmountMissing(debt, amount);
    }

    @Override
    public void editStatus(Debt debt, Status status) {
        repositoryDebt.updateStatus(debt, status);
    }
}