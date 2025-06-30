package com.empSystem.controllers;

import com.empSystem.abstracts.AuthService;
import com.empSystem.dtos.AuthResponse;
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

//    @GetMapping
//    public ResponseEntity<?> sayHi() {
//        return ResponseEntity.ok().body(new GlobalResponse<>("Hi", HttpStatus.CREATED));
//    }

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        AuthResponse authResponse = authService.register(request);
        return new ResponseEntity<>(new GlobalResponse<>(authResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }
}
