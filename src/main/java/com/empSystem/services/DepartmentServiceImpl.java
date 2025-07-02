package com.empSystem.services;

import com.empSystem.abstracts.DepartmentService;
import com.empSystem.dtos.DepartmentCreate;
import com.empSystem.dtos.DepartmentUpdate;
import com.empSystem.entities.Department;
import com.empSystem.entities.Employee;
import com.empSystem.exceptions.BadRequestException;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.DepartmentRepo;
import com.empSystem.repository.EmployeeRepo;
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

    @Autowired
    private EmployeeRepo employeeRepo;

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
        List<Employee> employeesInThisDepartment = employeeRepo.findAllByDepartmentId(id);
        if (!employeesInThisDepartment.isEmpty()) {
            throw new BadRequestException("Can't delete department with employees. Reassign or delete employees first");
        }

        Department department = departmentRepo.findById(id).orElseThrow(() ->
                new NotFoundException(localeMessage.getMessage("department.not.found", id.toString())));


        departmentRepo.delete(department);
    }

    @Override
    public Department findByName(String name) {
        return departmentRepo.findByName(name).orElseThrow(() -> new NotFoundException("Not Found Department under name: " + name));
    }
}
