package com.empSystem.entities;

import com.empSystem.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @NotNull(message = "{username.required}")
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @NotNull(message = "{password.required}")
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = "{email.error}")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "employee_id", unique = true)
    private Employee employee;

    @Column(name = "role", nullable = false)
    private Role role;

    public UserAccount() {
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
