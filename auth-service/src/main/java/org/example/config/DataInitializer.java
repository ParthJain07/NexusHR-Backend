package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entity.Employee;
import org.example.entity.UserAuth;
import org.example.enums.Role;
import org.example.repository.EmployeeRepository;
import org.example.repository.UserAuthRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final EmployeeRepository employeeRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            String adminEmail = "admin@zidio.in";

            if (!userAuthRepository.existsByEmail(adminEmail)) {
                Employee adminEmployee = Employee.builder()
                        .firstName("System")
                        .lastName("Admin")
                        .email(adminEmail)
                        .department("Management")
                        .designation("Chief Administrator")
                        .joiningDate(LocalDate.now())
                        .build();
                adminEmployee = employeeRepository.save(adminEmployee);

                // 2. Create Admin Credentials with ROLE_ADMIN
                UserAuth adminAuth = UserAuth.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ROLE_ADMIN)
                        .employee(adminEmployee)
                        .build();
                userAuthRepository.save(adminAuth);

                System.out.println("==========================================================");
                System.out.println("✅ Default Admin account created!");
                System.out.println("📧 Email:    admin@zidio.in");
                System.out.println("🔑 Password: admin123");
                System.out.println("==========================================================");
            }
        };
    }
}
