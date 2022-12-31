package com.ruppyrup.core.paymethods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PayMethod {
    protected String lastInstruction;
    abstract public void pay(String name, float amount);
    public String getLastInstruction() {
        return lastInstruction;
    }


}
