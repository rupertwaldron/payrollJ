package com.ruppyrup.core.paymethods;

import com.ruppyrup.core.models.Employee;

public class BankPayMethod extends PayMethod {
    private String accountNumber;

    public BankPayMethod(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void pay(String name, float amount) {
        String instruction = name+ " has been paid £" + amount + " into account number :: " + accountNumber;
        this.lastInstruction = instruction;
        System.out.println(instruction);
    }
}
