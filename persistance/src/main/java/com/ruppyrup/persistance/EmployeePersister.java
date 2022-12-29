package com.ruppyrup.persistance;

import com.ruppyrup.core.models.Employee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
public class EmployeePersister implements Persister<Employee> {

    private static final Map<Long, Employee> employees = new HashMap<>();
    private static long nextId;

    @Override
    public Employee get(long id) {
        return employees.get(id);
    }

    @Override
    public Iterator<Employee> getAll() {
        return employees.values().iterator();
    }

    @Override
    public void save(Employee employee) {
        employee.setId(nextId);
        employees.put(nextId++, employee);
    }
}