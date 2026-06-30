package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateEmployeeRequest;
import org.example.entity.Employee;
import org.example.entity.UserAuth;
import org.example.enums.Role;
import org.example.repository.EmployeeRepository;
import org.example.repository.UserAuthRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Transactional
    @CacheEvict(value = "employees", allEntries = true)
    public Employee createEmployeeWithRole(CreateEmployeeRequest request) {
        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Employee with email " + request.getEmail() + " already exists!");
        }

        Employee employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .department(request.getDepartment())
                .designation(request.getDesignation())
                .joiningDate(request.getJoiningDate() != null ? request.getJoiningDate() : java.time.LocalDate.now())
                .build();
        employee = employeeRepository.save(employee);

        Role assignedRole = request.getRole() != null ? request.getRole() : Role.ROLE_EMPLOYEE;
        String rawPassword = request.getPassword() != null ? request.getPassword() : "Welcome@123";

        UserAuth userAuth = UserAuth.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(rawPassword))
                .role(assignedRole)
                .employee(employee)
                .build();
        userAuthRepository.save(userAuth);

        return employee;
    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            throw new IllegalArgumentException("Employee with email " + employee.getEmail() + " already exists!");
        }
        if (employee.getJoiningDate() == null) {
            employee.setJoiningDate(java.time.LocalDate.now());
        }
        return employeeRepository.save(employee);
    }

    @Transactional
    @CacheEvict(value = "employees", allEntries = true)
    public void assignRoleToEmployee(Long employeeId, Role newRole) {
        Employee employee = getEmployeeById(employeeId);
        UserAuth userAuth = userAuthRepository.findByEmail(employee.getEmail())
                .orElseThrow(() -> new RuntimeException("User login credentials not found for employee"));
        userAuth.setRole(newRole);
        userAuthRepository.save(userAuth);
    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);
        existing.setFirstName(updatedEmployee.getFirstName());
        existing.setLastName(updatedEmployee.getLastName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setDepartment(updatedEmployee.getDepartment());
        existing.setDesignation(updatedEmployee.getDesignation());
        return employeeRepository.save(existing);
    }

    @CacheEvict(value = "employees", allEntries = true)
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
