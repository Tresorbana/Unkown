package com.template.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * PROJECT 1: University Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Students, Courses, Enrolments, Departments, Lecturers,
 *           Grades, Timetable, Fee payments, Results portal.
 */
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String code;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Lecturer> lecturers;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Course> courses;
}
