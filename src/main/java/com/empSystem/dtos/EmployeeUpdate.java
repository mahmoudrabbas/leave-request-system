package com.empSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EmployeeUpdate(
        @NotBlank(message = "{name.first.error}")
        String firstName,
        @NotBlank(message = "{name.last.error}")
        String lastName,
        @Pattern(regexp = "^01[0125][0-9]{8}$", message = "{phone.number.error}")
        String phoneNumber,
        @NotBlank(message = "{position.error}")
        String position,
        @NotBlank(message = "Department Is Required")
        String deptName
) {
}
