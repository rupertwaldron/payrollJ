package com.ruppyrup.operations.controller;

import com.ruppyrup.operations.factories.EmployeeFactory;
import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.paytypes.HourlyPayType;
import com.ruppyrup.operations.requests.EmployeeDTO;
import com.ruppyrup.operations.utilities.EmployeeConverter;
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
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        List<EmployeeDTO> employees = employeePersister.getAll().stream()
                .map(EmployeeConverter::from)
                .toList();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable long id) {
        Employee employee = employeePersister.get(id);
        return ResponseEntity.ok(EmployeeConverter.from(employee));
    }

    @PostMapping("/employees")
    public void saveEmployee(@RequestBody EmployeeDTO request) {
        Employee employee = employeeFactory.createEmployee(request);
        employeePersister.save(employee);
    }

    @PutMapping("/employees/{id}")
    public void updateHourlyWages(@PathVariable long id, @RequestBody EmployeeDTO request) {
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
