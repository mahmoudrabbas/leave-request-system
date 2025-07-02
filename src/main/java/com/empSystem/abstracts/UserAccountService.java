package com.empSystem.abstracts;

import com.empSystem.dtos.UserAccountResponse;
import com.empSystem.entities.UserAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserAccountService {
    List<UserAccountResponse> getAll();

    UserAccountResponse getOne(UUID id);

    Optional<UserAccount> findUserAccountByUsername(String username);

    void deleteOne(UUID id);


    void update(UUID userId, UserAccount userAccount);

    UserAccount findUserAccountByEmpId(UUID id);


}
