package org.example.demo.data.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.data.entities.enums.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Debt")

public class Debt {
    private static int counter;
    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
    private final List<Payment> paymentList = new ArrayList<>();
    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL)
    private final List<Detail> detailList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private int amountTotal;
    private int amountSent;
    private int amountMissing;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Debt() {
        id = ++counter;
        status = Status.ACCEPTED;
    }

    public Debt(int id, String date, int amountTotal, int amountSent, int amountMissing, Client client, Status status) {
        this.id = id;
        this.date = date;
        this.amountTotal = amountTotal;
        this.amountSent = amountSent;
        this.amountMissing = amountMissing;
        this.status = status;
    }

    public void addPayment(Payment payment) {
        paymentList.add(payment);
    }

    public void addDetail(Detail detail) {
        detailList.add(detail);
    }

    @Override
    public String toString() {
        return "Debt{" + "id=" + id + ", date='" + date + '\'' + ", amountTotal=" + amountTotal + ", amountSent=" + amountSent + ", amountMissing=" + amountMissing + ", status=" + status + '}';
    }
}