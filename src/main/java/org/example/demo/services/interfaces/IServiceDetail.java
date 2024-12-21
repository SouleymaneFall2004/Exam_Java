package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Detail;

import java.util.List;

public interface IServiceDetail extends Services<Detail> {
    List<Detail> fetchDetailsFromDebt(Debt debt);
}