package org.example.demo.data.repositories;

import org.example.demo.core.implementations.RepositoriesImpl;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;
import org.example.demo.data.repositories.interfaces.IRepositoryDetail;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryDetail extends RepositoriesImpl<Detail> implements IRepositoryDetail {
    @Override
    public List<Detail> findDetailsFromDebt(Debt debt) {
        return list.stream().filter(detail -> detail.getDebt().getId() == debt.getId()).collect(Collectors.toList());
    }
}