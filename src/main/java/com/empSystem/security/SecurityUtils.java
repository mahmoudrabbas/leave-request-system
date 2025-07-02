package com.empSystem.security;

import com.empSystem.entities.LeaveRequest;
import com.empSystem.entities.UserAccount;
import com.empSystem.exceptions.BadRequestException;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.LeaveRequestRepo;
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

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    public boolean isOwner(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepo.findByUsername(userDetails.getUsername());

        return userAccount.map(user -> user.getEmployee().getId().equals(id)).orElse(false);
    }

    public boolean isRealUser(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<UserAccount> userAccount = userAccountRepo.findByUsername(userDetails.getUsername());

        return userAccount.map(user -> user.getId().equals(id)).orElse(false);
    }


    public boolean isLeaveRequestOwner(UUID id) { // leave-request id
        System.out.println("ID>>>id");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return false;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserAccount userAccount = userAccountRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User Account is not found"));

        if (userAccount.getEmployee() == null) {
            throw new BadRequestException("Cant get this leave request, because it is not related to any employee");
        }

        LeaveRequest leaveRequest = leaveRequestRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Leave Request is not found"));

        System.out.println("it comes here");
        return userAccount.getEmployee().getId().equals(leaveRequest.getEmployee().getId());
    }

}
