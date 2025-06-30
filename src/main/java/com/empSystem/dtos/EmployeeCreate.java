package com.empSystem.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeCreate(
        @NotBlank(message = "{name.first.error}")
        String firstName,
        @NotBlank(message = "{name.last.error}")
        String lastName,
        @Email(message = "{email.error}")
        String email,
        @Pattern(regexp = "^01[0125][0-9]{8}$", message = "{phone.number.error}")
        String phoneNumber,
        @PastOrPresent(message = "{hire.date.error}")
        LocalDate hireDate,
        @NotBlank(message = "{position.error}")
        String position,
        @NotNull(message = "Department Id Is Required")
        UUID departmentId
) {
}
