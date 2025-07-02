package com.empSystem.controllers;

import com.empSystem.abstracts.LeaveRequestService;
import com.empSystem.dtos.LeaveRequestCreate;
import com.empSystem.dtos.LeaveRequestUpdate;
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
@RequestMapping("/leave-request")
public class LeaveRequestController {
    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isLeaveRequestOwner(#id)")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getOne(id), HttpStatus.OK), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addOne(@RequestBody @Valid LeaveRequestCreate entity) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.createOne(entity), HttpStatus.CREATED), HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isLeaveRequestOwner(#id)")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
        leaveRequestService.deleteOne(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/emp/{id}")
//    public ResponseEntity<?> getByEmpId(@PathVariable UUID id) {
//        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.getByEmployeeId(id), HttpStatus.OK), HttpStatus.OK);
//    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody LeaveRequestUpdate entity) {
        return new ResponseEntity<>(new GlobalResponse<>(leaveRequestService.updateStatus(id, entity), HttpStatus.OK), HttpStatus.OK);
    }


    @GetMapping("/emp/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isOwner(#id)")
    public ResponseEntity<?> getAllByEmpId(@PathVariable UUID id) {
        return ResponseEntity.ok().body(new GlobalResponse<>(leaveRequestService.getByEmployeeId(id), HttpStatus.OK));
    }

}
