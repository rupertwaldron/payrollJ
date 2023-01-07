package com.ruppyrup.businesslogic.paytypes;

public interface Hourly extends PayType {
    int getWeeklyHours();
    void setWeeklyHours(int weeklyHours);
}
