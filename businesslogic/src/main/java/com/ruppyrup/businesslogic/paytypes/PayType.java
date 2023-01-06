package com.ruppyrup.businesslogic.paytypes;

public interface PayType {
    float calculatePay();
    float getPay();

    int getWeeklyHours();
    void setWeeklyHours(int weeklyHours);
}
