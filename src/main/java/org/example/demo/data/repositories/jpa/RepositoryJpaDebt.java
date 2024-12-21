package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Client;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.enums.Status;
import org.example.demo.data.repositories.interfaces.IRepositoryDebt;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaDebt extends RepositoriesJpaImpl<Debt> implements IRepositoryDebt {
    public RepositoryJpaDebt() {
        open();
    }

    @Override
    public List<Debt> selectAll() {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d", Debt.class);
        return query.getResultList();
    }

    @Override
    public Debt findById(int id) {
        return EM.find(Debt.class, id);
    }

    @Override
    public List<Debt> findAllUnpaidDebts() {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.amountMissing > 0", Debt.class);
        return query.getResultList();
    }

    @Override
    public List<Debt> findAllUnpaidDebtsOfClient(Client client) {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.amountMissing > 0 AND d.client = :client", Debt.class);
        query.setParameter("client", client);
        return query.getResultList();
    }

    @Override
    public List<Debt> findAllPaidDebts() {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.amountMissing = 0", Debt.class);
        return query.getResultList();
    }

    @Override
    public List<Debt> findAllByStatus(Status status) {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.status = :status", Debt.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Debt> findAllPendingDebtsOfClient(Client client) {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.status = :status AND d.client = :client", Debt.class);
        query.setParameter("status", Status.PENDING);
        query.setParameter("client", client);
        return query.getResultList();
    }

    @Override
    public List<Debt> findAllCancelledDebtsOfClient(Client client) {
        TypedQuery<Debt> query = EM.createQuery("SELECT d FROM Debt d WHERE d.status = :status AND d.client = :client", Debt.class);
        query.setParameter("status", Status.CANCELLED);
        query.setParameter("client", client);
        return query.getResultList();
    }

    @Override
    public void updateDebtAmountSent(Debt debt, int amount) {
        try {
            EM.getTransaction().begin();
            debt.setAmountSent(amount);
            EM.merge(debt);
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }

    @Override
    public void updateDebtAmountMissing(Debt debt, int amount) {
        try {
            EM.getTransaction().begin();
            debt.setAmountMissing(amount);
            EM.merge(debt);
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }

    @Override
    public void updateStatus(Debt debt, Status status) {
        try {
            EM.getTransaction().begin();
            debt.setStatus(status);
            EM.merge(debt);
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }
}
