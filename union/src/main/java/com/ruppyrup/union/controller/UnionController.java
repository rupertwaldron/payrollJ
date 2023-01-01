package com.ruppyrup.union.controller;

import com.ruppyrup.core.models.Employee;
import com.ruppyrup.persistance.EmployeePersister;
import com.ruppyrup.union.requests.UnionRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class UnionController {

    private final EmployeePersister employeePersister;

    public UnionController(EmployeePersister employeePersister) {
        this.employeePersister = employeePersister;
    }

    @PutMapping("/union/{id}")
    public void updateUnionMembership(@PathVariable long id, @RequestBody UnionRequest request) {
        Employee employee = employeePersister.get(id);
        employee.setUnionMember(request.hasSignedUp());
    }
}
