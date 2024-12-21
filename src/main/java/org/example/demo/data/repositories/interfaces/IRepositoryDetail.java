package org.example.demo.data.repositories.interfaces;

import org.example.demo.core.Repositories;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;

import java.util.List;

public interface IRepositoryDetail extends Repositories<Detail> {
    List<Detail> findDetailsFromDebt(Debt debt);
}