package com.empSystem.abstracts;

import com.empSystem.dtos.EmployeeCreate;
import com.empSystem.dtos.EmployeeUpdate;
import com.empSystem.entities.Employee;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getEmployeeById(UUID id);

    void deleteOne(UUID id);

    Employee createOne(EmployeeCreate employee);

    Employee updateOne(UUID id, EmployeeUpdate employee);
}
