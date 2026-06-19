package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PROJECT 9: Online Exam System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Exams, Question bank, Auto-grading (MCQ), Manual grading (essays),
 *           Timer, Anti-cheat flags, Result analysis, Certificate generation.
 */
@Entity
@Table(name = "exam")
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "duration_minutes", nullable = false)
    private int durationMinutes;

    @Column(name = "total_marks", nullable = false)
    private int totalMarks;

    @Column(name = "pass_mark", nullable = false)
    private int passMark;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Question> questions;

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY)
    private List<ExamAttempt> attempts;
}
