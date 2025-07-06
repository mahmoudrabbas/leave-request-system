package com.empSystem.controllers;

import com.empSystem.abstracts.AuthService;
import com.empSystem.dtos.*;
import com.empSystem.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest request) {
        AuthResponse authResponse = authService.register(request);
        return new ResponseEntity<>(new GlobalResponse<>(authResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        String authResponse = authService.login(request);
        return new ResponseEntity<>(new GlobalResponse<>(authResponse, HttpStatus.CREATED), HttpStatus.CREATED);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestBody @Valid ForgetPassword request) {
        authService.initiatePasswordResetToken(request.email());
        return new ResponseEntity<>(new GlobalResponse<>("Password Reset Link Was Sent, please Check your Email!", HttpStatus.CREATED), HttpStatus.CREATED);
    }


    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody @Valid ResetPassword request) {
        authService.resetPassword(request);
        return new ResponseEntity<>(new GlobalResponse<>("Successfully Resetting Password", HttpStatus.CREATED), HttpStatus.CREATED);
    }


}
