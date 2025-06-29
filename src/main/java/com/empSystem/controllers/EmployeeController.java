package com.empSystem.controllers;

import com.empSystem.entities.Employee;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    @Autowired
    MessageSource messageSource;
    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public ResponseEntity<?> employees() {
        var resp = new GlobalResponse<List<Employee>>(employees, HttpStatus.OK);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee(@PathVariable UUID id) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId().equals(id)).findFirst();

        if (employee.isPresent()) {
            var resp = new GlobalResponse<Employee>(employee.get(), HttpStatus.OK);
            return ResponseEntity.ok(resp);
        }

        String[] arg = {id.toString()};
        String msg = messageSource.getMessage("emp.not.found", arg, LocaleContextHolder.getLocale());
        throw new NotFoundException(msg);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid Employee entity) {
        entity.setId(UUID.randomUUID());
        entity.setDepartmentId(UUID.randomUUID());
        employees.add(entity);

        var resp = new GlobalResponse<>(entity, HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid Employee entity) {
        Optional<Employee> employee = employees.stream().filter(emp -> emp.getId().equals(id)).findFirst();
        if (employee.isEmpty()) {
            throw new NotFoundException("Employee Not Found To Update");
        } else {
            Employee emp = employee.get();
            emp.setFirstName(entity.getFirstName());
            emp.setLastName(entity.getLastName());
            emp.setEmail(entity.getEmail());
            emp.setPosition(entity.getPosition());
            emp.setHireDate(entity.getHireDate());
            emp.setPhoneNumber(entity.getPhoneNumber());
            emp.setDepartmentId(entity.getDepartmentId());

            var resp = new GlobalResponse<>(entity, HttpStatus.CREATED);
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
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
