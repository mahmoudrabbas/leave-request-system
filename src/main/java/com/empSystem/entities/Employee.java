package com.empSystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "{name.first.error}")
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;
    @NotBlank(message = "{name.last.error}")
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;
    @Email(message = "{email.error}")
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    @Pattern(regexp = "^01[0125][0-9]{8}$", message = "{phone.number.error}")
    @Column(name = "phone_number", nullable = false, unique = true, length = 100)
    private String phoneNumber;
    @PastOrPresent(message = "{hire.date.error}")
    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;
    @NotBlank(message = "{position.error}")
    @Column(name = "position", nullable = false, length = 100)
    private String position;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {
    }

    public Employee(UUID id, String firstName, String lastName, String email, String phoneNumber, LocalDate hireDate, String position, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.position = position;
        this.department = department;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
