package com.empSystem.dtos;

import jakarta.validation.constraints.NotNull;

public record DepartmentCreate(
        @NotNull(message = "Department Is Required")
        String name
) {
    
}
