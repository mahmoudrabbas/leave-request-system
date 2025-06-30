package com.empSystem.controllers;

import com.empSystem.abstracts.DepartmentService;
import com.empSystem.dtos.DepartmentCreate;
import com.empSystem.dtos.DepartmentUpdate;
import com.empSystem.entities.Department;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> departments() {
        var resp = new GlobalResponse<List<Department>>(departmentService.getAll(), HttpStatus.OK);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> department(@PathVariable UUID id) {
        var resp = new GlobalResponse<Department>(departmentService.getDepartmentById(id), HttpStatus.OK);
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid DepartmentCreate entity) {

        var resp = new GlobalResponse<>(departmentService.createOne(entity), HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid DepartmentUpdate entity) {
        var resp = new GlobalResponse<>(departmentService.updateOne(id, entity), HttpStatus.CREATED);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID deptId) {
        departmentService.deleteOne(deptId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
