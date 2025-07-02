package com.empSystem.services;

import com.empSystem.abstracts.EmployeeService;
import com.empSystem.abstracts.LeaveRequestService;
import com.empSystem.abstracts.UserAccountService;
import com.empSystem.dtos.LeaveRequestCreate;
import com.empSystem.dtos.LeaveRequestUpdate;
import com.empSystem.entities.LeaveRequest;
import com.empSystem.entities.UserAccount;
import com.empSystem.enums.RequestStatus;
import com.empSystem.exceptions.NotFoundException;
import com.empSystem.repository.LeaveRequestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepo leaveRequestRepo;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserAccountService userAccountService;

    @Override
    public List<LeaveRequest> getAll() {
        return leaveRequestRepo.findAll();
    }

    @Override
    public LeaveRequest getOne(UUID id) {
        return leaveRequestRepo.findById(id).orElseThrow(() -> new NotFoundException("Request Is Not Found"));
    }

    @Override
    public LeaveRequest createOne(LeaveRequestCreate entity) {

        LeaveRequest leaveRequest = new LeaveRequest();

        UserAccount userAccount = userAccountService.findUserAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new NotFoundException("User Account is not found"));
        // last change here
        leaveRequest.setEmployee(userAccount.getEmployee());
        leaveRequest.setStartDate(entity.startDate());
        leaveRequest.setEndDate(entity.endDate());
        leaveRequest.setStatus(RequestStatus.PENDING);
        leaveRequest.setReason(entity.reason());
        leaveRequestRepo.save(leaveRequest);
        return leaveRequest;
    }

    @Override
    public LeaveRequest updateStatus(UUID id, LeaveRequestUpdate entity) {
        LeaveRequest leaveRequest = leaveRequestRepo
                .findById(id).orElseThrow(() -> new NotFoundException("Leave Is Not Found"));

        leaveRequest.setStatus(entity.status().equalsIgnoreCase("ACCEPTED") ? RequestStatus.ACCEPTED : RequestStatus.REJECTED);

        return leaveRequestRepo.save(leaveRequest);
    }

    @Override
    public void deleteOne(UUID id) {
        LeaveRequest leaveRequest = leaveRequestRepo.findById(id).orElseThrow(() -> new NotFoundException("Leave Is not found"));
        leaveRequestRepo.delete(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getByEmployeeId(UUID id) {
        return leaveRequestRepo.findByEmployeeId(id);
    }
}

