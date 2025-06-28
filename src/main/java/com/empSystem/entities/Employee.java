package com.empSystem.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.util.UUID;

public class Employee {
    private UUID id;

    @NotBlank(message = "{name.first.error}")
    private String firstName;
    @NotBlank(message = "{name.first.error}")
    private String lastName;
    @Email(message = "{email.error}")
    private String email;
    @Pattern(regexp = "^01[0125][0-9]{8}$", message = "{phone.number.error}")
    private String phoneNumber;
    @PastOrPresent(message = "{hire.date.error}")
    private LocalDate hireDate;
    @NotBlank(message = "{position.error}")
    private String position;

    private UUID departmentId;

    public Employee(UUID id, String firstName, String lastName, String email, String phoneNumber, LocalDate hireDate, String position, UUID departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.position = position;
        this.departmentId = departmentId;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }
}
