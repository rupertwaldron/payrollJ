package com.ruppyrup.persistance;

import com.ruppyrup.core.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeePersister implements Persister<Employee> {

    private final Map<Long, Employee> employees = new HashMap<>();
    private long nextId;

    @Override
    public Employee get(long id) {
        return employees.get(id);
    }

    @Override
    public List<Employee> getAll() {
        return employees.values().stream().toList();
    }

    @Override
    public void save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId++, employee);
    }

    @Override
    public void clearAll() {
        nextId = 0;
        employees.clear();
    }
}
