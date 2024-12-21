package org.example.demo.services;

import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;
import org.example.demo.data.repositories.interfaces.IRepositoryDetail;
import org.example.demo.services.interfaces.IServiceDetail;

import java.util.List;

public class ServiceDetail implements IServiceDetail {
    private final IRepositoryDetail repositoryDetail;

    public ServiceDetail(IRepositoryDetail repositoryDetail) {
        this.repositoryDetail = repositoryDetail;
    }
    @Override
    public void save(Detail object) {
        repositoryDetail.insert(object);
    }

    @Override
    public List<Detail> show() {
        return repositoryDetail.selectAll();
    }

    @Override
    public List<Detail> fetchDetailsFromDebt(Debt debt) {
        return repositoryDetail.findDetailsFromDebt(debt);
    }
}