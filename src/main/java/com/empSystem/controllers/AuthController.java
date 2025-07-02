package com.empSystem.controllers;

import com.empSystem.abstracts.AuthService;
import com.empSystem.dtos.AuthResponse;
import com.empSystem.dtos.LoginRequest;
import com.empSystem.dtos.SignupRequest;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        System.out.println("1");
        AuthResponse authResponse = authService.register(request);
        System.out.println("2");
        return new ResponseEntity<>(new GlobalResponse<>(authResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String authResponse = authService.login(request);
        return new ResponseEntity<>(new GlobalResponse<>(authResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }
}
