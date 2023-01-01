package com.ruppyrup.operations.utilities;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paymethods.BankPayMethod;
import com.ruppyrup.core.paytypes.HourlyPayType;
import com.ruppyrup.core.paytypes.SalaryPayType;
import com.ruppyrup.operations.requests.EmployeeDTO;

public final class EmployeeConverter {

    public static EmployeeDTO from(Employee employee) {

        float salary = 0;
        float hourlyRate = 0;
        int weeklyHours = 0;

        if (employee.getPayType() instanceof SalaryPayType) {
            salary = ((SalaryPayType)employee.getPayType()).getSalary();
        } else {
            hourlyRate = ((HourlyPayType)employee.getPayType()).getHourlyRate();
            weeklyHours = ((HourlyPayType)employee.getPayType()).getWeeklyHours();
        }

        return new EmployeeDTO(
                employee.getName(),
                employee.getPayMethod().getClass().getSimpleName(),
                employee.getPaySchedule().getClass().getSimpleName(),
                employee.getPayType().getClass().getSimpleName(),
                salary,
                hourlyRate,
                ((BankPayMethod)employee.getPayMethod()).getAccountNumber(),
                weeklyHours
        );
    }
}
