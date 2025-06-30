package com.empSystem.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record LeaveRequestCreate(
        @NotNull(message = "{start.date.error}")
        @FutureOrPresent(message = "{start.date.invalid}")
        LocalDate startDate,
        @NotNull(message = "{end.date.error}")
        @FutureOrPresent(message = "{end.date.invalid}")
        LocalDate endDate,
        @NotNull(message = "Employee Id is required")
        UUID employeeId,
        @NotNull(message = "Reason is required")
        String reason
) {
}
