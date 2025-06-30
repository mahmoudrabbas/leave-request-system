package com.empSystem.abstracts;

import com.empSystem.entities.UserAccount;

import java.util.Optional;
import java.util.UUID;

public interface UserAccountService {
    Optional<UserAccount> getOne(UUID id);

    UserAccount createOne(UserAccount userAccount);

    UserAccount updateOne(UserAccount userAccount);

    Optional<UserAccount> findUserAccountByUsername(String username);

    void deleteOne(UUID id);

}
