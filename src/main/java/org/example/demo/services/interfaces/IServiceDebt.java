package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;

import java.util.List;

public interface IServiceDebt extends Services<Debt> {
    Debt fetchById(int object);
    List<Debt> fetchAllUnpaidDebts();
    List<Debt> fetchAllUnpaidDebtsOfClient(Client client);
    List<Debt> fetchAllPendingDebtsOfClient(Client client);
    List<Debt> fetchAllCancelledDebtsOfClient(Client client);
    List<Debt> fetchAllPaidDebts();
    List<Debt> fetchAllByStatus(Status status);
    void editDebtAmountSent(Debt debt, int amount);
    void editDebtAmountMissing(Debt debt, int amount);
    void editStatus(Debt debt, Status status);
}