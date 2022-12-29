package com.ruppyrup.core.requests;

public record CreateEmployeeRequest(
    String name,
    String payMethod,
    String paySchedule,
    String payType,
    float salary,
    float hourlyRate,
    String accountNumber,
    int weeklyHours
    ){}
