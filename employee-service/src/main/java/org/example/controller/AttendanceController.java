package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClockInRequest;
import org.example.entity.Attendance;
import org.example.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/clock-in")
    public ResponseEntity<String> clockIn(@RequestBody ClockInRequest request) {
        return ResponseEntity.ok(attendanceService.clockIn(request.getEmployeeId()));
    }

    @PostMapping("/clock-out")
    public ResponseEntity<String> clockOut(@RequestBody ClockInRequest request) {
        return ResponseEntity.ok(attendanceService.clockOut(request.getEmployeeId()));
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<List<Attendance>> getAttendanceHistory(@PathVariable Long employeeId) {
        return ResponseEntity.ok(attendanceService.getMyAttendance(employeeId));
    }
}
