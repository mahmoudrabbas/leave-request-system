package com.empSystem.security;

import com.empSystem.entities.UserAccount;
import com.empSystem.repository.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SecurityUtils {
    @Autowired
    private UserAccountRepo userAccountRepo;

    public boolean isOwner(UUID id) {
        System.out.println("Is Owner");
        System.out.println("id>>>>>>>" + id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepo.findByUsername(userDetails.getUsername());

        System.out.println("emp id from authentication" + userAccount.get().getEmployee().getId());
        System.out.println("true? " + userAccount.map(user -> user.getEmployee().getId().equals(id)).orElse(false));
        return userAccount.map(user -> user.getEmployee().getId().equals(id)).orElse(false);
    }

    public boolean isRealUser(UUID id) {
        System.out.println("Is REal User");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepo.findByUsername(userDetails.getUsername());

        return userAccount.map(user -> user.getId().equals(id)).orElse(false);
    }


    public boolean isRealUser(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepo.findByUsername(userDetails.getUsername());

        return userAccount.map(user -> user.getUsername().equals(username)).orElse(false);
    }

}
