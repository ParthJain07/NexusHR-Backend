package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthResponse;
import org.example.dto.LoginRequest;
import org.example.dto.SignupRequest;
import org.example.entity.Employee;
import org.example.entity.UserAuth;
import org.example.enums.Role;
import org.example.repository.EmployeeRepository;
import org.example.repository.UserAuthRepository;
import org.example.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final EmployeeRepository employeeRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        if (userAuthRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }


        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .build();
        employee = employeeRepository.save(employee);


        UserAuth userAuth = UserAuth.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_EMPLOYEE) // Default role
                .employee(employee)
                .build();
        userAuthRepository.save(userAuth);


        String token = jwtUtil.generateToken(userAuth.getEmail(), userAuth.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("User registered successfully")
                .build();
    }

    public AuthResponse login(LoginRequest request) {

        UserAuth userAuth = userAuthRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Verify hashed password
        if (!passwordEncoder.matches(request.getPassword(), userAuth.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }


        String token = jwtUtil.generateToken(userAuth.getEmail(), userAuth.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}
