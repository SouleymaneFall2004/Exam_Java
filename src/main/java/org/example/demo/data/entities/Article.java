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
@Table(name = "Article")

public class Article {
    private static int counter;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String label;
    private int stock;
    private int price;
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final List<Detail> detailList = new ArrayList<>();

    public Article() {
        id = ++counter;
    }

    public Article(String label, int stock, int price) {
        id = ++counter;
        this.label = label;
        this.stock = stock;
        this.price = price;
    }

    public Article(int id, String label, int stock, int price) {
        this.id = id;
        this.label = label;
        this.stock = stock;
        this.price = price;
    }

    public void addDetail(Detail detail) {
        detailList.add(detail);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}