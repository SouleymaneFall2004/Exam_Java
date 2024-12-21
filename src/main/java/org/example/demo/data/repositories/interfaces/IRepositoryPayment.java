package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;

import java.util.List;

public interface IRepositoryPayment extends Repositories<Payment> {
    List<Payment> findPaymentsFromDebt(Debt debt);
}