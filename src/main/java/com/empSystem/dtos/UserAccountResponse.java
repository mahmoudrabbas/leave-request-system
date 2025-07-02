package com.empSystem.dtos;

import com.empSystem.entities.Employee;
import com.empSystem.enums.Role;

import java.util.UUID;

public class UserAccountResponse {
    private UUID id;
    private String username;
    private Employee employee;
    private Role role;

    public UserAccountResponse() {
    }

    public UserAccountResponse(UUID id, String username, Employee employee, Role role) {
        this.id = id;
        this.username = username;
        this.employee = employee;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
