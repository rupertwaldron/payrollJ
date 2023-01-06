package com.ruppyrup.operations.factories;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.models.EmployeeImpl;
import com.ruppyrup.operations.requests.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactory {

    public Employee createEmployee(EmployeeDTO employeeRequest) {
        if (employeeRequest.payType() == null) {
            throw new RuntimeException("Error creating employee " + employeeRequest);
        }

        Employee employee = getEmployee();
        employee.setName(employeeRequest.name());
        employee.setBankPayMethod(employeeRequest.accountNumber());
        employee.setPaySchedule(employeeRequest.payType());

        switch (employeeRequest.payType()) {
            case "monthly" -> employee.setSalaryPayType(employeeRequest.pay());
            case "weekly" -> employee.setHourlyPayType(employeeRequest.pay());
            default -> throw new RuntimeException("Error creating employee " + employeeRequest);
        }
        return employee;
    }

    @Lookup
    public Employee getEmployee() {
        return null;
    }
}
