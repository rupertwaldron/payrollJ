package com.ruppyrup.persistance;

import java.util.List;

public interface Persister<T> {
    T get(long id);
    List<T> getAll();
    void save(T t);

    void clearAll();
}
