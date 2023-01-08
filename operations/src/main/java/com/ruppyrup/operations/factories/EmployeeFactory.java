package com.ruppyrup.operations.factories;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.operations.requests.EmployeeDTO;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component
public class EmployeeFactory {

    public Employee createEmployee(EmployeeDTO employeeRequest) {
        if (employeeRequest.payType() == null) {
            throw new RuntimeException("Error creating employee " + employeeRequest);
        }

        Employee employee = getEmployee();
        employee.setName(employeeRequest.name());
        employee.setPayMethod(employeeRequest.accountNumber());
        employee.setPaySchedule(employeeRequest.paySchedule());
        employee.setPayType(employeeRequest.payType(), employeeRequest.pay());
        return employee;
    }

    @Lookup
    public Employee getEmployee() {
        return null;
    }
}
