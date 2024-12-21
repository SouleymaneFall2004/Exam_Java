package org.example.demo.data.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Client")

public class Client {
    private static int counter;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private final List<Debt> debtList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String surname;
    private String phone;
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    public Client() {
        id = ++counter;
    }

    public Client(String surname, String phone, String address) {
        id = ++counter;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public Client(int id, String surname, String phone, String address) {
        this.id = id;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public Client(String surname, String phone, String address, Account account) {
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.account = account;
    }

    public void addDebt(Debt debt) {
        debtList.add(debt);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", surname='" + surname + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            '}';
    }
}