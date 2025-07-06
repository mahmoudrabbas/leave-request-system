package com.empSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record EmployeeCreate(
        @NotBlank(message = "{name.first.error}")
        String firstName,
        @NotBlank(message = "{name.last.error}")
        String lastName,
        @Pattern(regexp = "^01[0125][0-9]{8}$", message = "{phone.number.error}")
        String phoneNumber,
        @PastOrPresent(message = "{hire.date.error}")
        LocalDate hireDate,
        @NotBlank(message = "{position.error}")
        String position,
        @NotNull(message = "Department Name Id Is Required")
        String deptName
) {
}
