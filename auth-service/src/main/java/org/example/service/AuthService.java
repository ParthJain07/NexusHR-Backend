package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthResponse;
import org.example.dto.LoginRequest;
import org.example.dto.SignupRequest;
import org.example.entity.Employee;
import org.example.enums.Role;
import org.example.repository.EmployeeRepository;
import org.example.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        // Create new employee and hash the password using Argon2
        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_EMPLOYEE) // Default role
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .build();

        employeeRepository.save(employee);

        // Generate JWT Token
        String token = jwtUtil.generateToken(employee.getEmail(), employee.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify the Argon2 hashed password
        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT Token
        String token = jwtUtil.generateToken(employee.getEmail(), employee.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}
