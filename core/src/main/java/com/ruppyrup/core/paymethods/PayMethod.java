package com.ruppyrup.core.paymethods;

import com.ruppyrup.core.models.Employee;

import java.io.Serializable;

public abstract class PayMethod implements Serializable {
    protected String lastInstruction;
    abstract public void pay(String name, float amount);
    public String getLastInstruction() {
        return lastInstruction;
    }
}
