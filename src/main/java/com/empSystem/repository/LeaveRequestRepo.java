package com.empSystem.repository;

import com.empSystem.entities.LeaveRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeaveRequestRepo extends JpaRepository<LeaveRequest, UUID> {

    @Override
    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<LeaveRequest> findById(UUID uuid);

    @Override
    @EntityGraph(attributePaths = {"employee", "employee.department"})
    List<LeaveRequest> findAll();

    @EntityGraph(attributePaths = {"employee", "employee.department"})
    List<LeaveRequest> findByEmployeeId(UUID uuid);

}
