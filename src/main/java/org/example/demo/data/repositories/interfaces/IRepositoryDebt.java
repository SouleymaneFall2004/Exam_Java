package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;

import java.util.List;

public interface IRepositoryDebt extends Repositories<Debt> {
    Debt findById(int id);
    List<Debt> findAllUnpaidDebts();
    List<Debt> findAllUnpaidDebtsOfClient(Client client);
    List<Debt> findAllPaidDebts();
    List<Debt> findAllByStatus(Status status);
    List<Debt> findAllPendingDebtsOfClient(Client client);
    List<Debt> findAllCancelledDebtsOfClient(Client client);
    void updateDebtAmountSent(Debt debt, int amount);
    void updateDebtAmountMissing(Debt debt, int amount);
    void updateStatus(Debt debt, Status status);
}