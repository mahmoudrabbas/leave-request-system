package com.empSystem.dtos;

import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @NotNull(message = "{username.required}")
        String username,
        @NotNull(message = "{password.required}")
        String password
) {
}
