package com.empSystem.repository;

import com.empSystem.entities.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {


    @Override
    @EntityGraph(attributePaths = {"employee", "employee.department"})
    List<UserAccount> findAll();

    @Override
    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<UserAccount> findById(UUID uuid);

    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<UserAccount> findByUsername(String username);

    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<UserAccount> findByEmail(String email);


    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<UserAccount> findByEmployeeId(UUID id);

}
