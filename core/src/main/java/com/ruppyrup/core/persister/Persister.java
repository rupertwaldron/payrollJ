package com.ruppyrup.core.persister;

import com.ruppyrup.core.models.Employee;

import java.util.Iterator;

public interface Persister<T> {
    T get(long id);
    Iterator<T> getAll();
    void save(T t);

}
