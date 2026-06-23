package org.example.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class LeaveRequestDto {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType; // e.g., "SICK", "VACATION"
    private String reason;
}