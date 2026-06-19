package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT 7: HR Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Employees, Departments, Payroll, Leave management,
 *           Performance reviews, Recruitment, Attendance tracking.
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "base_salary", precision = 12, scale = 2)
    private BigDecimal baseSalary;

    @Enumerated(EnumType.STRING)
    private EmploymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
    private List<Employee> subordinates;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<LeaveRequest> leaveRequests;

    public enum EmploymentStatus { ACTIVE, ON_LEAVE, TERMINATED, PROBATION }
}
