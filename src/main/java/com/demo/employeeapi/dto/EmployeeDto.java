package com.demo.employeeapi.dto;

import java.math.BigDecimal;

public record EmployeeDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    String title,
    String department,
    Long managerId,
    BigDecimal salary,
    String ssn
) {}
