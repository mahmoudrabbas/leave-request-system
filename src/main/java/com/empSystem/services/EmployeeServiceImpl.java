package com.empSystem.services;

import com.empSystem.abstracts.DepartmentService;
import com.empSystem.abstracts.EmployeeService;
import com.empSystem.abstracts.UserAccountService;
import com.empSystem.dtos.EmployeeCreate;
import com.empSystem.dtos.EmployeeUpdate;
import com.empSystem.entities.Department;
import com.empSystem.entities.Employee;
import com.empSystem.entities.UserAccount;
import com.empSystem.exceptions.AlreadyExistsException;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.EmployeeRepo;
import com.empSystem.security.SecurityUtils;
import com.empSystem.utils.LocaleMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private LocaleMessage localeMessage;
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public List<Employee> getAll() {
        return employeeRepo.findAll();
    }


    public Employee getEmployeeById(UUID id) {
        return employeeRepo.findById(id).orElseThrow(() -> new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString())));
    }


    public void deleteOne(UUID id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString())));

        UserAccount userAccount = userAccountService.findUserAccountByEmpId(employee.getId());

        userAccount.setEmployee(null);

        userAccountService.update(userAccount.getId(), userAccount);

        employeeRepo.delete(employee);
    }

    @Override
    public Employee createOne(EmployeeCreate entity) {
        Employee employee = new Employee();
        Department department = departmentService.findByName(entity.deptName());

        UserAccount userAccount = userAccountService
                .findUserAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("Not Authorized"));

        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());


        if (userAccount.getEmployee() != null)
            throw new AlreadyExistsException("You Already have Employee Account");


        employee.setFirstName(entity.firstName());
        employee.setLastName(entity.lastName());
        employee.setEmail(entity.email());
        employee.setPosition(entity.position());
        employee.setHireDate(entity.hireDate());
        employee.setPhoneNumber(entity.phoneNumber());

        employee.setDepartment(department);


        employeeRepo.save(employee);

        // changed
        userAccount.setEmployee(employee);
        userAccountService.update(userAccount.getId(), userAccount);


        return employee;
    }

    @Override
    @PreAuthorize("@securityUtils.isOwner(#id)")
    public Employee updateOne(UUID id, EmployeeUpdate entity) { // deptName
        Employee emp = employeeRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(localeMessage.getMessage("emp.not.found", id.toString())));
        Department department = departmentService.findByName(entity.deptName());

        emp.setDepartment(department);

        emp.setFirstName(entity.firstName());
        emp.setLastName(entity.lastName());
        emp.setPosition(entity.position());
        emp.setPhoneNumber(entity.phoneNumber());


        return employeeRepo.save(emp);
    }


}
