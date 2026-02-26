package com.demo.employeeapi.controller;

import com.demo.employeeapi.dto.EmployeeDto;
import com.demo.employeeapi.service.EmployeeService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

  private final EmployeeService service;

  public EmployeeController(EmployeeService service) {
    this.service = service;
  }


  @GetMapping("/{id}")
  public EmployeeDto getById(@PathVariable Long id, Authentication auth) {
    return service.getEmployee(id, auth);
  }
}
