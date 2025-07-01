package com.empSystem.security;

import com.empSystem.abstracts.UserAccountService;
import com.empSystem.entities.UserAccount;
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
    private UserAccountService userAccountService;

    public boolean isOwner(UUID id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountService.findUserAccountByUsername(userDetails.getUsername());

        return userAccount.map(user -> user.getEmployee().getId().equals(id)).orElse(false);
    }

//    public boolean isAdmin()
}
