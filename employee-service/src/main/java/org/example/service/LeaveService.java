package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LeaveRequestDto;
import org.example.entity.Employee;
import org.example.entity.LeaveRequest;
import org.example.repository.EmployeeRepository;
import org.example.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final EmployeeRepository employeeRepository;

    public String submitLeaveRequest(LeaveRequestDto dto) {
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest leave = LeaveRequest.builder()
                .employee(employee)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .leaveType(dto.getLeaveType())
                .reason(dto.getReason())
                .status("PENDING") // Automatically set to PENDING!
                .build();

        leaveRepository.save(leave);
        return "Leave request submitted successfully. Status is PENDING.";
    }

    public List<LeaveRequest> getMyLeaves(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveRequest> getAllPendingLeaves() {
        return leaveRepository.findByStatus("PENDING");
    }

    public String updateLeaveStatus(Long leaveId, String status) {
        LeaveRequest leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leave.setStatus(status);
        leaveRepository.save(leave);
        return "Leave request #" + leaveId + " has been marked as " + status;
    }
}
