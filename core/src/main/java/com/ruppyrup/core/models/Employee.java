package com.ruppyrup.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String name;
    private Long id;

    public Employee(String name) {
        this.name = name;
    }
}
