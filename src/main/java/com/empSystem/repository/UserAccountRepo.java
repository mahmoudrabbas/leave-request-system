package com.empSystem.repository;

import com.empSystem.entities.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, UUID> {
    @EntityGraph(attributePaths = {"employee", "employee.department"})
    Optional<UserAccount> findByUsername(String username);
}
