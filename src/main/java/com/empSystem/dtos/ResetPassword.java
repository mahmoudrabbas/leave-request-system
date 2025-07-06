package com.empSystem.dtos;

import jakarta.validation.constraints.NotNull;

public record ResetPassword(
        @NotNull(message = "token is required")
        String token,
        @NotNull(message = "Enter a new password")
        String newPassword
) {
}
