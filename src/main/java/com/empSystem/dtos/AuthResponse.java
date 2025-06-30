package com.empSystem.dtos;

import com.empSystem.entities.Employee;

import java.util.UUID;

public class AuthResponse {
    private UUID id;
    private String username;
    private Employee employee;

    public AuthResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
