package com.empSystem.controllers;

import com.empSystem.abstracts.EmployeeService;
import com.empSystem.dtos.EmployeeCreate;
import com.empSystem.dtos.EmployeeUpdate;
import com.empSystem.entities.Employee;
import com.empSystem.security.SecurityUtils;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> employees() {
        var resp = new GlobalResponse<>(employeeService.getAll(), HttpStatus.OK);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("@securityUtils.isOwner(#id) or hasRole('ADMIN')")
    public ResponseEntity<?> employee(@PathVariable UUID id) {
        var resp = new GlobalResponse<Employee>(employeeService.getEmployeeById(id), HttpStatus.OK);
        return ResponseEntity.ok(resp);
    }


    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@RequestBody @Valid EmployeeCreate entity) {
        var resp = new GlobalResponse<>(employeeService.createOne(entity), HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@securityUtils.isOwner(#id) or hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid EmployeeUpdate entity) {
        System.out.println("A");
        var resp = new GlobalResponse<>(employeeService.updateOne(id, entity), HttpStatus.CREATED);
        System.out.println("F");
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("@securityUtils.isOwner(#empId) or hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable("id") UUID empId) {
        System.out.println("A");
        employeeService.deleteOne(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
