package com.empSystem.controllers;

import com.empSystem.abstracts.EmployeeService;
import com.empSystem.dtos.EmployeeCreate;
import com.empSystem.dtos.EmployeeUpdate;
import com.empSystem.entities.Employee;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> employees() {
        var resp = new GlobalResponse<>(employeeService.getAll(), HttpStatus.OK);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee(@PathVariable UUID id) {
        var resp = new GlobalResponse<Employee>(employeeService.getEmployeeById(id), HttpStatus.OK);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeCreate entity) {

        var resp = new GlobalResponse<>(employeeService.createOne(entity), HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid EmployeeUpdate entity) {
        var resp = new GlobalResponse<>(employeeService.updateOne(id, entity), HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID empId) {
        employeeService.deleteOne(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
