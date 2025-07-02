package com.empSystem.controllers;

import com.empSystem.abstracts.UserAccountService;
import com.empSystem.entities.UserAccount;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.security.SecurityUtils;
import com.empSystem.shared.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserAccount userAccount;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public ResponseEntity<?> allUsers() {
        return ResponseEntity.ok(new GlobalResponse<>(userAccountService.getAll(), HttpStatus.OK));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(new GlobalResponse<>(userAccountService.getOne(id), HttpStatus.OK));
    }

    @PreAuthorize("hasRole('ADMIN') or #username==authentication.name")
    @GetMapping("/username")
    public ResponseEntity<?> getOneByUsername(@RequestParam("username") String username) {
        return userAccountService.findUserAccountByUsername(username)
                .map(user -> ResponseEntity.ok().body(new GlobalResponse<>(user, HttpStatus.OK)))
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isRealUser(#id)")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
        userAccountService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
