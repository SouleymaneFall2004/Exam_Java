package org.example.demo.data.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "Detail")

public class Detail {
    private static int counter;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "debt_id", referencedColumnName = "id")
    private Debt debt;

    public Detail() {
        id = ++counter;
    }

    public Detail(int id, int stock, Article article, Debt debt ) {
        this.id = id;
        this.stock = stock;
        this.article = article;
        this.debt = debt;
    }

    public Detail(Article article, Debt debt, int stock) {
        id = ++counter;
        this.article = article;
        this.debt = debt;
        this.stock = stock;
    }
}