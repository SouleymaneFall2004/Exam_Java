package org.example.demo.services.interfaces;

import org.example.demo.core.Services;
import org.example.demo.data.entities.Debt;
import org.example.demo.data.entities.Payment;

import java.util.List;

public interface IServicePayment extends Services<Payment> {
    List<Payment> fetchPaymentsFromDebt(Debt debt);
}