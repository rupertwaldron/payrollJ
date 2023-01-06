package com.ruppyrup.core.models;

import com.ruppyrup.businesslogic.payschedules.PaySchedule;

public interface Employee {

    void pay();
    float getPay();
    String getPayMethod();
    String getPaySchedule();
    String getPayType();
    String getName();
    String getPaymentDetails();
    int getWeeklyHours();
    String getLastPaymentInstruction();
    boolean isUnionMember();
    void setBankPayMethod(String accountNumber);
    void setSalaryPayType(float salary);
    void setHourlyPayType(float hourlyRate);
    void setPaySchedule(String type);
    void setWeeklyHours(int weeklyHours);
    void setId(long id);
    void setUnionMember(boolean hasSignedUp);
    void setName(String name);
}
