package com.ruppyrup.core.paytypes;

import lombok.Data;

@Data
public class SalaryPayType implements PayType {

    private float salary;

    public SalaryPayType(float salary) {
        this.salary = salary;
    }

    @Override
    public float calculatePay() {
        return salary / 12;
    }
}
