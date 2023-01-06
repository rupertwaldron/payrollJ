package com.ruppyrup.operations.utilities;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.operations.requests.EmployeeDTO;

public final class EmployeeConverter {

    private EmployeeConverter() {
    }

    public static EmployeeDTO fromEmployee(Employee employee) {

        return new EmployeeDTO(
                employee.getName(),
                employee.getPayMethod(),
                employee.getPaySchedule(),
                employee.getPayType(),
                employee.getPay(),
                employee.getPaymentDetails(),
                employee.getWeeklyHours(),
                employee.getLastPaymentInstruction(),
                employee.isUnionMember()
        );
    }
}
