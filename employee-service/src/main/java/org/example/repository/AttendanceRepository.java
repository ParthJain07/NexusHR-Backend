package org.example.repository;

import org.example.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Find a specific employee's attendance for today
    Optional<Attendance> findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    // Get all attendance records for an employee
    List<Attendance> findByEmployeeId(Long employeeId);
}
