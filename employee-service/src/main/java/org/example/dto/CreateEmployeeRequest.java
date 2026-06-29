package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.Role;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String designation;
    private LocalDate joiningDate;
    
    // Dynamic Role and Credentials assigned by HR/Admin
    private Role role;       // e.g., ROLE_MANAGER, ROLE_HR, ROLE_EMPLOYEE
    private String password; // Default password (e.g., Welcome@123)
}
