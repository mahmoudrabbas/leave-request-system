package com.empSystem.abstracts;

import com.empSystem.dtos.AuthResponse;
import com.empSystem.dtos.LoginRequest;
import com.empSystem.dtos.ResetPassword;
import com.empSystem.dtos.SignupRequest;

public interface AuthService {
    AuthResponse register(SignupRequest request);

    String login(LoginRequest request);

    void initiatePasswordResetToken(String email);

    void resetPassword(ResetPassword resetPassword);
}
