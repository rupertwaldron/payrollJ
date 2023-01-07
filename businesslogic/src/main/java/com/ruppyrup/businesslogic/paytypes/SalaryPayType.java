package com.ruppyrup.businesslogic.paytypes;

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

    @Override
    public float getPay() {
        return salary;
    }

//    @Override
//    public int getWeeklyHours() {
//        //ToDo ISP violation
//        return -1;
//    }
//
//    @Override
//    public void setWeeklyHours(int weeklyHours) {
//        //ToDo ISP violation
//    }
}
