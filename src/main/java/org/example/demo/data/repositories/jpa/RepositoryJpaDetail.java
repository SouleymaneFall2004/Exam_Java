package org.example.demo.data.repositories.jpa;

import org.example.demo.core.implementations.RepositoriesJpaImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;
import org.example.demo.data.repositories.interfaces.IRepositoryDetail;

import javax.persistence.TypedQuery;
import java.util.List;

public class RepositoryJpaDetail extends RepositoriesJpaImpl<Detail> implements IRepositoryDetail {
    public RepositoryJpaDetail() {
        open();
    }

    @Override
    public List<Detail> selectAll() {
        TypedQuery<Detail> query = EM.createQuery("SELECT d FROM Detail d", Detail.class);
        return query.getResultList();
    }

    @Override
    public List<Detail> findDetailsFromDebt(Debt debt) {
        TypedQuery<Detail> query = EM.createQuery("SELECT d FROM Detail d WHERE d.debt.id = :debtId", Detail.class);
        query.setParameter("debtId", debt.getId());
        return query.getResultList();
    }
}
