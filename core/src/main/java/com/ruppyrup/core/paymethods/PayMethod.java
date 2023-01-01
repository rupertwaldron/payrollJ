package com.ruppyrup.core.paymethods;

public interface PayMethod {

    void pay(String name, float amount);
    String getLastInstruction();

    String getPaymentDetails();


}
