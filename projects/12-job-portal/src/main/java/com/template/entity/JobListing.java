package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT 12: Job Portal
 * Stack: Spring Boot + Hibernate + MySQL + Reactive (WebFlux) + JUnit
 *
 * Features: Job listings, Candidate profiles, Applications, Resume upload,
 *           Employer dashboard, Search/filter, Email notifications.
 */
@Entity
@Table(name = "job_listing")
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;

    @Column(name = "salary_min", precision = 12, scale = 2)
    private BigDecimal salaryMin;

    @Column(name = "salary_max", precision = 12, scale = 2)
    private BigDecimal salaryMax;

    @Column(nullable = false)
    private String location;

    @Column(name = "closing_date")
    private LocalDate closingDate;

    @Column(name = "is_active")
    private boolean active;

    @OneToMany(mappedBy = "jobListing", fetch = FetchType.LAZY)
    private List<Application> applications;

    public enum JobType { FULL_TIME, PART_TIME, CONTRACT, INTERNSHIP, REMOTE }
}
