package com.demo.employeeapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  private String title;
  private String department;

  @Column(name = "manager_id")
  private Long managerId;

  // Sensitive
  @Column(precision = 19, scale = 2)
  private BigDecimal salary;

  // Sensitive (demo)
  @Column(length = 16)
  private String ssn;

  @Column(name = "updated_at")
  private OffsetDateTime updatedAt = OffsetDateTime.now();

  @PreUpdate
  void onUpdate() { this.updatedAt = OffsetDateTime.now(); }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public String getDepartment() { return department; }
  public void setDepartment(String department) { this.department = department; }

  public Long getManagerId() { return managerId; }
  public void setManagerId(Long managerId) { this.managerId = managerId; }

  public BigDecimal getSalary() { return salary; }
  public void setSalary(BigDecimal salary) { this.salary = salary; }

  public String getSsn() { return ssn; }
  public void setSsn(String ssn) { this.ssn = ssn; }

  public OffsetDateTime getUpdatedAt() { return updatedAt; }
  public void setUpdatedAt(OffsetDateTime updatedAt) { this.updatedAt = updatedAt; }
}
