package com.empSystem.abstracts;

import com.empSystem.dtos.LeaveRequestCreate;
import com.empSystem.entities.LeaveRequest;

import java.util.List;
import java.util.UUID;

public interface LeaveRequestService {
    List<LeaveRequest> getAll();

    LeaveRequest getOne(UUID id);

    LeaveRequest createOne(LeaveRequestCreate entity);

    void deleteOne(UUID id);

    List<LeaveRequest> getByEmployeeId(UUID id);

}
