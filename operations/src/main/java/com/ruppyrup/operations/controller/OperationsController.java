package com.ruppyrup.operations.controller;

import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paytypes.HourlyPayType;
import com.ruppyrup.core.persister.EmployeePersister;
import com.ruppyrup.operations.requests.CreateEmployeeRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
public class OperationsController {

    private final EmployeePersister employeePersister;
    private final EmployeeFactory employeeFactory;

    public OperationsController(EmployeePersister employeePersister, EmployeeFactory employeeFactory) {
        this.employeePersister = employeePersister;
        this.employeeFactory = employeeFactory;
    }

    @GetMapping("/employees")
    public Iterator<Employee> getEmployees() {
        return employeePersister.getAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployee(@PathVariable long id) {
        return employeePersister.get(id);
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody CreateEmployeeRequest request) {
        Employee employee = employeeFactory.createEmployee(request);
        employeePersister.save(employee);
    }

    @PatchMapping("/employees/{id}")
    public void updateHourlyWages(@PathVariable long id, @RequestBody CreateEmployeeRequest request) {
        Employee employee = employeePersister.get(id);
        try {
            ((HourlyPayType) employee.getPayType()).setWeeklyHours(request.weeklyHours());
        } catch (ClassCastException cce) {
            throw new RuntimeException("Employee " + id + " is not an hourly employee");
        }
    }

    @GetMapping("/employees/paynow")
    public void payAllEmployees() {
        employeePersister.getAll().forEachRemaining(Employee::pay);
    }
}
