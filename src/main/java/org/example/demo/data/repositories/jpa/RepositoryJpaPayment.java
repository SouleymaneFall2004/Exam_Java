package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;
import org.example.demo.data.repositories.interfaces.IRepositoryPayment;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaPayment extends RepositoriesJpaImpl<Payment> implements IRepositoryPayment {
    public RepositoryJpaPayment() {
        open();
    }

    @Override
    public List<Payment> selectAll() {
        TypedQuery<Payment> query = EM.createQuery("SELECT p FROM Payment p", Payment.class);
        return query.getResultList();
    }

    @Override
    public List<Payment> findPaymentsFromDebt(Debt debt) {
        TypedQuery<Payment> query = EM.createQuery("SELECT p FROM Payment p WHERE p.debt.id = :debtId", Payment.class);
        query.setParameter("debtId", debt.getId());
        return query.getResultList();
    }
}
