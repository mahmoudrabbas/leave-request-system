package com.empSystem.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DepartmentUpdate(
        UUID id,
        @NotNull(message = "Please Enter The Department Name")
        String name
) {
}
