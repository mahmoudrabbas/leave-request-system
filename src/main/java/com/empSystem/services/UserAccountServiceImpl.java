package com.empSystem.services;

import com.empSystem.abstracts.UserAccountService;
import com.empSystem.dtos.UserAccountResponse;
import com.empSystem.entities.UserAccount;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.UserAccountRepo;
import com.empSystem.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountRepo userAccountRepo;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserAccountResponse> getAll() {
        return userAccountRepo.findAll().stream().map(user -> new UserAccountResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmployee(),
                        user.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isRealUser(#id)")
    public UserAccountResponse getOne(UUID id) {
        return userAccountRepo.findById(id).map(user -> new UserAccountResponse(user.getId(),
                user.getUsername(),
                user.getEmployee(),
                user.getRole())).orElseThrow(() -> new NotFoundException("Not Found"));
    }


    @Override
    @PreAuthorize("hasRole('ADMIN') or @securityUtils.isRealUser(#id)")
    public void deleteOne(UUID id) {
        UserAccount userAccount = userAccountRepo.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        userAccountRepo.delete(userAccount);
    }

    @Override
    public Optional<UserAccount> findUserAccountByUsername(String username) {
        return userAccountRepo.findByUsername(username);
    }

    @PreAuthorize("@securityUtils.isRealUser(#userId)")
    public void update(UUID userId, UserAccount userAccount) {
        userAccountRepo.save(userAccount);
    }

    @Override
    public UserAccount findUserAccountByEmpId(UUID id) {
        return userAccountRepo.findByEmployeeId(id).orElseThrow(() -> new NotFoundException("User Account is not found"));
    }

}
