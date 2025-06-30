package com.empSystem.controllers;

import com.empSystem.abstracts.LeaveRequestService;
import com.empSystem.dtos.LeaveRequestCreate;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/leave-request")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getOne(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addOne(@RequestBody @Valid LeaveRequestCreate entity) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.createOne(entity), HttpStatus.CREATED), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
        leaveRequestService.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/emp/{id}")
    public ResponseEntity<?> getByEmpId(@PathVariable UUID id) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getByEmployeeId(id), HttpStatus.OK), HttpStatus.OK);
    }


}
