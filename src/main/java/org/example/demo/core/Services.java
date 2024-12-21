package org.example.demo.core;

import java.util.List;

public interface Services<T> {
    void save(T object);
    List<T> show();
}