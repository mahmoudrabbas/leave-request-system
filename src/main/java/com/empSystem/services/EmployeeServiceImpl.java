package com.empSystem.services;

import com.empSystem.abstracts.DepartmentService;
import com.empSystem.abstracts.EmployeeService;
import com.empSystem.dtos.EmployeeCreate;
import com.empSystem.dtos.EmployeeUpdate;
import com.empSystem.entities.Department;
import com.empSystem.entities.Employee;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.EmployeeRepo;
import com.empSystem.security.SecurityUtils;
import com.empSystem.utils.LocaleMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    ArrayList<Employee> employees = new ArrayList<>();

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private LocaleMessage localeMessage;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }

    @PreAuthorize("@securityUtils.isOwner(#id) or hasRole('ADMIN')")
    public Employee getEmployeeById(UUID id) {
        return employeeRepo.findById(id).orElseThrow(() -> new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString())));
    }

    @Override
    public void deleteOne(UUID id) {
        Optional<Employee> employee = employeeRepo.findById(id);
        if (employee.isPresent()) {
            employees.remove(employee.get());
        } else {
            throw new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString()));
        }
    }

    @Override
    public Employee createOne(EmployeeCreate entity) {
        Employee employee = new Employee();
        Department department = departmentService.getDepartmentById(entity.departmentId());

        employee.setFirstName(entity.firstName());
        employee.setLastName(entity.lastName());
        employee.setEmail(entity.email());
        employee.setPosition(entity.position());
        employee.setHireDate(entity.hireDate());
        employee.setPhoneNumber(entity.phoneNumber());

        employee.setDepartment(department);

        employeeRepo.save(employee);

        return employee;
    }

    @Override
    public Employee updateOne(UUID id, EmployeeUpdate entity) {
        Employee emp = employeeRepo.findById(id).orElseThrow(() -> new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString())));
        Department department = departmentService.getDepartmentById(emp.getId());
        department.setName(entity.deptName());
        emp.setDepartment(department);

        emp.setFirstName(entity.firstName());
        emp.setLastName(entity.lastName());
        emp.setPosition(entity.position());
        emp.setPhoneNumber(entity.phoneNumber());


        return employeeRepo.save(emp);
    }

}
