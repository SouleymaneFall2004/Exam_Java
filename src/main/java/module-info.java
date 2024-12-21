module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires org.yaml.snakeyaml;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires annotations;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.controllers;
    exports org.example.demo.core;
    opens org.example.demo.core.implementations;
    exports org.example.demo.services;
    exports org.example.demo.services.interfaces;
    exports org.example.demo.data.entities;
    exports org.example.demo.data.entities.enums;
    exports org.example.demo.data.repositories.jpa;
    exports org.example.demo.data.repositories.database;
    exports org.example.demo.data.repositories.interfaces;
    opens org.example.demo.controllers to javafx.fxml;
}