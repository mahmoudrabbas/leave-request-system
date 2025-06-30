package com.empSystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record SignupRequest(
        @NotNull(message = "{username.required}")
        String username,
        @NotNull(message = "{password.required}")
        String password,
        @NotNull(message = "Employee is Required")
        UUID employeeId
) {
}
