package com.empSystem.repository;

import com.empSystem.entities.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, UUID> {

    @EntityGraph(attributePaths = {"department"})
    Optional<Employee> findById(UUID id);

    @EntityGraph(attributePaths = {"department"})
    List<Employee> findAll();

}
