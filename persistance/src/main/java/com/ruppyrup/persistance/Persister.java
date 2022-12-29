package com.ruppyrup.persistance;

import java.util.Iterator;

public interface Persister<T> {
    T get(long id);
    Iterator<T> getAll();
    void save(T t);

}
