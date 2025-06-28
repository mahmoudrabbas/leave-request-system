package com.empSystem.controllers;

import com.empSystem.entities.Employee;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> employees() {
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee(@PathVariable UUID id) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId().equals(id)).findFirst();

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Is not Found");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Employee entity) {
        entity.setId(UUID.randomUUID());
        entity.setDepartmentId(UUID.randomUUID());
        employees.add(entity);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Employee entity) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId().equals(id)).findFirst();
        if (employee.isEmpty()) {
            return new ResponseEntity<>("Employee Not Found To Update", HttpStatus.NOT_FOUND);
        } else {
            Employee emp = employee.get();
            emp.setFirstName(entity.getFirstName());
            emp.setLastName(entity.getLastName());
            emp.setEmail(entity.getEmail());
            emp.setPosition(entity.getPosition());
            emp.setHireDate(entity.getHireDate());
            emp.setPhoneNumber(entity.getPhoneNumber());
            emp.setDepartmentId(entity.getDepartmentId());
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID empId) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId().equals(empId)).findFirst();
        if (employee.isEmpty()) return new ResponseEntity<>("Employee Not Found To Delete", HttpStatus.NOT_FOUND);
        employees.remove(employee.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
