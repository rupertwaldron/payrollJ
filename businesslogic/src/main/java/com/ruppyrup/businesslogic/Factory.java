package com.ruppyrup.businesslogic;

public interface Factory<T> {
    T retreive(String type);
}
