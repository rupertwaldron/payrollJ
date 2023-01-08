package com.ruppyrup.core.models;

public interface Employee {

    void pay();
    float getPay();
    String getPayMethod();
    String getPaySchedule();
    String getPayType();
    String getName();
    String getPaymentDetails();

    String getEmployeeInfo();

    int getWeeklyHours();
    String getLastPaymentInstruction();
    boolean isUnionMember();
    void setPayMethod(String accountNumber);
    void setPaySchedule(String type);
    void setWeeklyHours(int weeklyHours);
    void setId(long id);
    void setUnionMember(boolean hasSignedUp);
    void setName(String name);
    void setPayType(String payType, float pay);
}
