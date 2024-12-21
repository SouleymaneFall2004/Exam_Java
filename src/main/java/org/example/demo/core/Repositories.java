package org.example.demo.core;

import java.util.List;

public interface Repositories<T> {
    void insert(T object);
    List<T> selectAll();
}