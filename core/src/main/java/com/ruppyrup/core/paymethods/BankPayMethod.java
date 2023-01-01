package com.ruppyrup.core.paymethods;

import lombok.Data;


public class BankPayMethod implements PayMethod {
    private String accountNumber;
    protected String lastInstruction;

    public BankPayMethod(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void pay(String name, float amount) {
        String instruction = name+ " has been paid £" + amount + " into account number :: " + accountNumber;
        this.lastInstruction = instruction;
        System.out.println(instruction);
    }

    @Override
    public String getLastInstruction() {
        return lastInstruction;
    }

    @Override
    public String getPaymentDetails() {
        return accountNumber;
    }

    @Override
    public String toString() {
        return "BankPayMethod{" +
                "accountNumber='" + accountNumber + '\'' +
                ", lastInstruction='" + lastInstruction + '\'' +
                '}';
    }
}
