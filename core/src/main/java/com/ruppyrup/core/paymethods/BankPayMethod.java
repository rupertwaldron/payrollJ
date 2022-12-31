package com.ruppyrup.core.paymethods;

public class BankPayMethod extends PayMethod {
    private String accountNumber;

    public BankPayMethod(String accountNumber) {
        super();
        this.accountNumber = accountNumber;
    }

    @Override
    public void pay(String name, float amount) {
        String instruction = name+ " has been paid £" + amount + " into account number :: " + accountNumber;
        this.lastInstruction = instruction;
        System.out.println(instruction);
    }

    @Override
    public String toString() {
        return "BankPayMethod{" +
                "accountNumber='" + accountNumber + '\'' +
                ", lastInstruction='" + lastInstruction + '\'' +
                '}';
    }
}
