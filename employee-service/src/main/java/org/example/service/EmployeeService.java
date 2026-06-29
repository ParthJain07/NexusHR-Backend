package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee createEmployee(Employee employee) {
        if (employee.getJoiningDate() == null) {
            employee.setJoiningDate(java.time.LocalDate.now());
        }
        return employeeRepository.save(employee);
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
