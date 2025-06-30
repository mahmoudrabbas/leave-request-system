package com.empSystem.abstracts;

import com.empSystem.dtos.DepartmentCreate;
import com.empSystem.dtos.DepartmentUpdate;
import com.empSystem.entities.Department;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    List<Department> getAll();

    Department getDepartmentById(UUID id);

    Department createOne(DepartmentCreate department);

    Department updateOne(UUID id, DepartmentUpdate entity);

    void deleteOne(UUID id);

}
