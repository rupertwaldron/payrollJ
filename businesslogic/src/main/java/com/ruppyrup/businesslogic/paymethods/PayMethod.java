package com.ruppyrup.businesslogic.paymethods;

public interface PayMethod {

    void pay(String name, float amount);
    String getLastInstruction();

    String getPaymentDetails();


}
