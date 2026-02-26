package com.demo.employeeapi.service;

import com.demo.employeeapi.dto.EmployeeDto;
import com.demo.employeeapi.repo.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

  private final EmployeeRepository employeeRepo;
  private final PermissionService permissionService;

  public EmployeeService(EmployeeRepository employeeRepo, PermissionService permissionService) {
    this.employeeRepo = employeeRepo;
    this.permissionService = permissionService;
  }

  public EmployeeDto getEmployee(Long id, Authentication auth) {
    if (!permissionService.hasPermission(auth, "EMPLOYEE_READ")) {
      throw new AccessDeniedException("Missing EMPLOYEE_READ");
    }

    var emp = employeeRepo.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

    boolean sensitive = permissionService.hasPermission(auth, "EMPLOYEE_SENSITIVE_READ");

    return new EmployeeDto(
        emp.getId(),
        emp.getFirstName(),
        emp.getLastName(),
        emp.getEmail(),
        emp.getTitle(),
        emp.getDepartment(),
        emp.getManagerId(),
        sensitive ? emp.getSalary() : null,
        sensitive ? emp.getSsn() : null
    );
  }
}
