package com.ruppyrup.operations.controller;

import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paytypes.HourlyPayType;
import com.ruppyrup.operations.requests.CreateEmployeeRequest;
import com.ruppyrup.persistance.EmployeePersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationsController {

    private final EmployeePersister employeePersister;
    private final EmployeeFactory employeeFactory;

    public OperationsController(EmployeePersister employeePersister, EmployeeFactory employeeFactory) {
        this.employeePersister = employeePersister;
        this.employeeFactory = employeeFactory;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeePersister.getAll();
        return ResponseEntity.ok(employees);
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
        employeePersister.getAll().forEach(Employee::pay);
    }
}
