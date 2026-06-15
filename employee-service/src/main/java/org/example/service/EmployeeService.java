package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    // Note: You will need to copy the EmployeeRepository interface we made in Auth-Service
    // into the Employee-Service as well so it can access the database!
    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
