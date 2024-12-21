package org.example.demo.data.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Payment")

public class Payment {
    private static int counter;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private int amount;
    @ManyToOne
    @JoinColumn(name = "debt_id", referencedColumnName = "id")
    private Debt debt;

    public Payment() {
        id = ++counter;
    }

    public Payment(int id, String date, int amount) {
        this.id = id;
        this.date = date;
        this.amount = amount;
    }

    public Payment(String date, int amount, Debt debt) {
        this.date = date;
        this.amount = amount;
        this.debt = debt;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", amount=" + amount +
                '}';
    }
}