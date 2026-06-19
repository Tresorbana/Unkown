package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT 4: Hospital Management System
 * Stack: Spring Boot + Hibernate + MySQL + Spring Security + JUnit + Reactive
 *
 * Features: Patients, Doctors, Appointments, Medical records,
 *           Prescriptions, Billing, Ward management, Lab results.
 */
@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(length = 10)
    private String bloodGroup;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MedicalRecord> medicalRecords;
}
