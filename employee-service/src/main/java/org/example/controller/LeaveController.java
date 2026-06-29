package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.LeaveRequestDto;
import org.example.entity.LeaveRequest;
import org.example.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    // Any logged in employee can submit a request
    @PostMapping("/request")
    @PreAuthorize("hasAuthority('APPLY_LEAVE')")
    public ResponseEntity<String> submitLeave(@RequestBody LeaveRequestDto request) {
        return ResponseEntity.ok(leaveService.submitLeaveRequest(request));
    }

    // Employees can see their own history
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getMyLeaves(@PathVariable Long employeeId) {
        return ResponseEntity.ok(leaveService.getMyLeaves(employeeId));
    }

    // ONLY HR/Admin can see all pending requests
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ROLE_HR', 'ROLE_ADMIN')")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaves() {
        return ResponseEntity.ok(leaveService.getAllPendingLeaves());
    }

    // ONLY HR/Admin can approve or reject
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('APPROVE_LEAVE')")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        // e.g. /api/leaves/1/status?status=APPROVED
        return ResponseEntity.ok(leaveService.updateLeaveStatus(id, status));
    }
}
