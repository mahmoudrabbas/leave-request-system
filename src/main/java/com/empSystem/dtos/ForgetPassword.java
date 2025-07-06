package com.empSystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ForgetPassword(
        @Email(message = "Enter a valid email")
        @NotNull(message = "Enter your Email you registered by")
        String email
) {
}
