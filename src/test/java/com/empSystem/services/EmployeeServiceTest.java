package com.empSystem.services;

import com.empSystem.entities.Department;
import com.empSystem.entities.Employee;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.EmployeeRepo;
import com.empSystem.utils.LocaleMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    private final String UUID_ = "d77b76e7-440d-4a33-a7e6-8e5bc6fcde38";
    @Mock
    private EmployeeRepo employeeRepo;
    @Mock
    private LocaleMessage localeMessage;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    public void testGetEmployeeById() {
        Employee employee = new Employee(UUID.fromString(UUID_),
                "Mahmoud", "Abbas", "mr@gmail.com", "01024348948",
                LocalDate.of(2025, 5, 5), "Software Engineer",
                new Department(UUID.fromString(UUID_), "IT"));

        Mockito.when(employeeRepo.findById(UUID.fromString(UUID_))).thenReturn(Optional.of(employee));

        Employee res = employeeService.getEmployeeById(UUID.fromString(UUID_));

        Assertions.assertNotNull(res);
        Assertions.assertEquals("Mahmoud", res.getFirstName());
    }

    @Test
    public void testEmployeeNotFound() {

        String expectedMessage = "Employee Not found with id: " + UUID_;
        Mockito.when(employeeRepo.findById(UUID.fromString(UUID_))).thenReturn(Optional.empty());
        Mockito.when(localeMessage.getMessage(ArgumentMatchers.eq("emp.not.found"), ArgumentMatchers.eq(UUID_))).thenReturn(expectedMessage);

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () ->
                employeeService.getEmployeeById(UUID.fromString(UUID_)));

        Assertions.assertEquals(expectedMessage, ex.getMessage());

    }

    @Test
    public void testGetAllEmployees() {

        List<Employee> employees = List.of(
                new Employee(UUID.fromString(UUID_), "Mahmoud", "Abbas", "mr@gmail.com", "01012345677",
                        LocalDate.of(2025, 5, 5), "Software Engineer", new Department(UUID.fromString(UUID_), "IT")),
                new Employee(UUID.fromString(UUID_), "Ali", "Mohamed", "am@gmail.com", "01023456789",
                        LocalDate.of(2025, 5, 6), "Sales Man", new Department(UUID.fromString(UUID_), "IT")),
                new Employee(UUID.fromString(UUID_), "Mostafa", "Hishmat", "mh@gmail.com", "01023456788",
                        LocalDate.of(2025, 5, 7), "Recruiter", new Department(UUID.fromString(UUID_), "Hr"))
        );

        Mockito.when(employeeRepo.findAll()).thenReturn(employees);

        List<Employee> res = employeeService.getAll();
        Assertions.assertEquals(3, res.size());
        Assertions.assertEquals("Mahmoud", res.get(0).getFirstName());
        Assertions.assertEquals("mr@gmail.com", res.get(0).getEmail());
    }


}
