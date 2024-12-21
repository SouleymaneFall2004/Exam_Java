package org.example.demo.core.implementations;

import org.example.demo.core.Repositories;

import java.util.ArrayList;
import java.util.List;

public abstract class RepositoriesImpl<T> implements Repositories<T> {
    protected List<T> list = new ArrayList<>();

    @Override
    public void insert(T object) {
        list.add(object);
    }
    @Override
    public List<T> selectAll() {
        return list;
    }
}