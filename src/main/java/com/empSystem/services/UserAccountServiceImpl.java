package com.empSystem.services;

import com.empSystem.abstracts.UserAccountService;
import com.empSystem.entities.UserAccount;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountRepo userAccountRepo;

    @Override
    public Optional<UserAccount> getOne(UUID id) {
        return userAccountRepo.findById(id);
    }

    @Override
    public UserAccount createOne(UserAccount userAccount) {
        return userAccountRepo.save(userAccount);
    }

    @Override
    public UserAccount updateOne(UserAccount userAccount) {
        return userAccountRepo.save(userAccount);
    }

    @Override
    public void deleteOne(UUID id) {
        UserAccount userAccount = userAccountRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        userAccountRepo.delete(userAccount);
    }

    @Override
    public Optional<UserAccount> findUserAccountByUsername(String username) {
        return userAccountRepo.findByUsername(username);
    }
}
