package org.example.demo.core.implementations;

import org.example.demo.core.Repositories;
import org.yaml.snakeyaml.Yaml;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.InputStream;
import java.util.Map;

public abstract class RepositoriesJpaImpl<T> implements Repositories<T> {
    protected EntityManager EM;
    protected EntityManagerFactory EMF;

    public RepositoriesJpaImpl() {

    }

    @Override
    public void insert(T object) {
        try {
            open();
            EM.getTransaction().begin();
            EM.persist(object);
            EM.flush();
            EM.getTransaction().commit();
        } catch (Exception e) {
            if (EM.getTransaction().isActive()) {
                EM.getTransaction().rollback();
            }
            e.fillInStackTrace();
        }
    }

    public void open() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.yaml")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.yaml not found");
            }

            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            if (config == null || !config.containsKey("persistence")) {
                throw new IllegalStateException("Missing 'persistence' section in config.yaml");
            }

            Map<String, Object> persistenceConfig = (Map<String, Object>) config.get("persistence");
            String activeUnit = (String) persistenceConfig.get("activeUnit");

            if (activeUnit == null || activeUnit.isEmpty()) {
                throw new IllegalStateException("No 'activeUnit' specified in persistence section of config.yaml");
            }

            EMF = Persistence.createEntityManagerFactory(activeUnit);

            if (EM == null) {
                EM = EMF.createEntityManager();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading persistence configuration", e);
        }
    }

    public void close() {
        if (EM != null && EM.isOpen()) {
            EM.close();
        }
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}