package com.ruppyrup.operations.controller;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.core.persister.EmployeePersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Iterator;

@RestController
public class OperationsController {

    private final EmployeePersister employeePersister;

    public OperationsController(EmployeePersister employeePersister) {
        this.employeePersister = employeePersister;
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
    public void getEmployee(@RequestBody Employee employee) {
        employeePersister.save(employee);
    }

}
