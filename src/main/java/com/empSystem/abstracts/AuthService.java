package com.empSystem.abstracts;

import com.empSystem.dtos.AuthResponse;
import com.empSystem.dtos.SignupRequest;

public interface AuthService {
    AuthResponse register(SignupRequest request);

//    AuthResponse login(LoginRequest request);
}
