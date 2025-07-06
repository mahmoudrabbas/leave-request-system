package com.empSystem.repository;

import com.empSystem.entities.PasswordRestToken;
import com.empSystem.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PasswordRestTokenRepo extends JpaRepository<PasswordRestToken, UUID> {
    Optional<PasswordRestToken> findByToken(String token);

    Optional<PasswordRestToken> findByUserAccount(UserAccount userAccount);
}
