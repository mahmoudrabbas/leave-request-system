package com.empSystem.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @NotNull(message = "{username.required}")
        String username,
        @Email(message = "{email.error}")
        String email,
        @NotNull(message = "{password.required}")
        String password
) {
}
