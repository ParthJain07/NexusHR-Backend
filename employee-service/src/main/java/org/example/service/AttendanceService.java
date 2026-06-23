package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Attendance;
import org.example.entity.Employee;
import org.example.repository.AttendanceRepository;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public String clockIn(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        // Check if already clocked in today
        if (attendanceRepository.findByEmployeeIdAndDate(employeeId, today).isPresent()) {
            return "You have already clocked in today!";
        }

        Attendance attendance = Attendance.builder()
                .employee(employee)
                .date(today)
                .clockInTime(LocalTime.now())
                .status("PRESENT")
                .build();

        attendanceRepository.save(attendance);
        return "Clocked in successfully at " + LocalTime.now();
    }

    public String clockOut(Long employeeId) {
        LocalDate today = LocalDate.now();
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(employeeId, today)
                .orElseThrow(() -> new RuntimeException("No clock-in record found for today!"));

        if (attendance.getClockOutTime() != null) {
            return "You have already clocked out today!";
        }

        attendance.setClockOutTime(LocalTime.now());
        attendanceRepository.save(attendance);
        return "Clocked out successfully at " + LocalTime.now();
    }

    public List<Attendance> getMyAttendance(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }
}
