package org.example.demo.services;

import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;
import org.example.demo.data.repositories.interfaces.IRepositoryPayment;
import org.example.demo.services.interfaces.IServicePayment;

import java.util.List;

public class ServicePayment implements IServicePayment {
    private final IRepositoryPayment repositoryPayment;

    public ServicePayment(IRepositoryPayment repositoryPayment) {
        this.repositoryPayment = repositoryPayment;
    }
    @Override
    public void save(Payment object) {
        repositoryPayment.insert(object);
    }

    @Override
    public List<Payment> show() {
        return repositoryPayment.selectAll();
    }

    @Override
    public List<Payment> fetchPaymentsFromDebt(Debt debt) {
        return repositoryPayment.findPaymentsFromDebt(debt);
    }
}