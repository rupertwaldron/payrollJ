package com.ruppyrup.operations.requests;

public record EmployeeDTO(
    String name,
    String payMethod,
    String paySchedule,
    String payType,
    float pay,
    String accountNumber,
    int weeklyHours,
    String lastInstruction,
    boolean isUnionMember
    ){}
