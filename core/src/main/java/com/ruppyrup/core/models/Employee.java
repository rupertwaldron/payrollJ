package com.ruppyrup.core.models;

public interface Employee {
    void pay();
    String getName();

    String getEmployeeInfo();
    void setPayMethod(String accountNumber);
    void setPaySchedule(String type);
    void setWeeklyHours(int weeklyHours);
    void setId(long id);
    void setUnionMember(boolean hasSignedUp);
    void setName(String name);
    void setPayType(String payType, float pay);
}
