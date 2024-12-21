package org.example.demo.data.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.example.demo.data.entities.enums.Role;
import org.example.demo.data.entities.enums.State;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Account")

public class Account {
    private static int counter;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne(mappedBy = "account")
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Account() {
        id = ++counter;
        this.state = State.ENABLED;
    }

    public Account(String login, String password, Role role) {
        id = ++counter;
        this.login = login;
        this.password = password;
        this.role = role;
        this.state = State.ENABLED;
    }

    public Account(int id, String login, String password, Role role, State state, Client client) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.state = state;
        this.client = client;
    }

    public Account(String login, String password, Role role, State state) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", state=" + state.toString() +
                '}';
    }
}