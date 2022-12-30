package com.ruppyrup.operations.factories;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paymethods.BankPayMethod;
import com.ruppyrup.core.payschedules.MonthlyPaySchedule;
import com.ruppyrup.core.payschedules.WeeklyPaySchedule;
import com.ruppyrup.core.paytypes.HourlyPayType;
import com.ruppyrup.core.paytypes.SalaryPayType;
import com.ruppyrup.operations.requests.CreateEmployeeRequest;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactory {

    public Employee createEmployee(CreateEmployeeRequest employeeRequest) {
        if (employeeRequest.payType() == null) {
            throw new RuntimeException("Error creating employee " + employeeRequest);
        }
        Employee employee = new Employee(employeeRequest.name());
        employee.setPayMethod(new BankPayMethod(employeeRequest.accountNumber()));
        switch (employeeRequest.payType()) {
            case "monthly" -> {
                employee.setPayType(new SalaryPayType(employeeRequest.salary()));
                employee.setPaySchedule(new MonthlyPaySchedule());
            }
            case "hourly" -> {
                employee.setPayType(new HourlyPayType(employeeRequest.hourlyRate()));
                employee.setPaySchedule(new WeeklyPaySchedule());
            }
            default -> throw new RuntimeException("Error creating employee " + employeeRequest);
        }
        return employee;
    }
}
