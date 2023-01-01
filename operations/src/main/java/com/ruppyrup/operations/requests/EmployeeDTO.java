package com.ruppyrup.operations.requests;

public record EmployeeDTO(
    String name,
    String payMethod,
    String paySchedule,
    String payType,
    float salary,
    float hourlyRate,
    String accountNumber,
    int weeklyHours
    ){}
