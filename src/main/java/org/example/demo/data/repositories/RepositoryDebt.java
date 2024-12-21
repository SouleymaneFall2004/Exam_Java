package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.data.repositories.interfaces.IRepositoryDebt;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryDebt extends RepositoriesImpl<Debt> implements IRepositoryDebt {
    @Override
    public Debt findById(int id) {
        return list.stream().filter(debt -> debt.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Debt> findAllUnpaidDebts() {
        return list.stream().filter(debt -> debt.getAmountMissing() != 0).collect(Collectors.toList());
    }

    @Override
    public List<Debt> findAllUnpaidDebtsOfClient(Client client) {
        return findAllUnpaidDebts().stream().filter(debt -> debt.getClient() == client ).collect(Collectors.toList());
    }

    @Override
    public List<Debt> findAllPendingDebtsOfClient(Client client) {
        return findAllByStatus(Status.PENDING).stream().filter(debt -> debt.getClient() == client ).collect(Collectors.toList());
    }

    @Override
    public List<Debt> findAllCancelledDebtsOfClient(Client client) {
        return findAllByStatus(Status.CANCELLED).stream().filter(debt -> debt.getClient() == client ).collect(Collectors.toList());
    }

    @Override
    public List<Debt> findAllPaidDebts() {
        return list.stream().filter(debt -> debt.getAmountMissing() == 0).collect(Collectors.toList());
    }

    @Override
    public List<Debt> findAllByStatus(Status status) {
        return list.stream().filter(debt -> debt.getStatus() == status).collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Debt debt, Status status) {
        debt.setStatus(status);
    }

    @Override
    public void updateDebtAmountSent(Debt debt, int amount) {
        debt.setAmountSent(amount);
    }

    @Override
    public void updateDebtAmountMissing(Debt debt, int amount) {
        debt.setAmountMissing(amount);
    }
}