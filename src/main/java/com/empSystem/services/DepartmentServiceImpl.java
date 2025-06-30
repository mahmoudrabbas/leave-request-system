package com.empSystem.services;

import com.empSystem.abstracts.DepartmentService;
import com.empSystem.dtos.DepartmentCreate;
import com.empSystem.dtos.DepartmentUpdate;
import com.empSystem.entities.Department;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.DepartmentRepo;
import com.empSystem.utils.LocaleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private LocaleMessage localeMessage;

    @Override
    public List<Department> getAll() {
        return departmentRepo.findAll();
    }

    @Override
    public Department getDepartmentById(UUID id) {
        return departmentRepo.findById(id).orElseThrow(() ->
                new NotFoundException(localeMessage.getMessage("dept.not.found", id.toString())));
    }

    @Override
    public Department createOne(DepartmentCreate entity) {
        Department department = new Department();
        department.setName(entity.name());
        departmentRepo.save(department);
        return department;
    }

    @Override
    public Department updateOne(UUID id, DepartmentUpdate entity) {
        Department department = departmentRepo.findById(id).orElseThrow(() ->
                new NotFoundException(localeMessage.getMessage("department.not.found", id.toString())));

        department.setName(entity.name());
        return departmentRepo.save(department);
    }

    @Override
    public void deleteOne(UUID id) {
        Department department = departmentRepo.findById(id).orElseThrow(() ->
                new NotFoundException(localeMessage.getMessage("department.not.found", id.toString())));

        departmentRepo.delete(department);
    }
}
