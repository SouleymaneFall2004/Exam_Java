package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;
import org.example.demo.data.repositories.interfaces.IRepositoryPayment;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryPayment extends RepositoriesImpl<Payment> implements IRepositoryPayment {
    @Override
    public List<Payment> findPaymentsFromDebt(Debt debt) {
        return list.stream().filter(payment -> payment.getDebt().getId() == debt.getId()).collect(Collectors.toList());
    }
}