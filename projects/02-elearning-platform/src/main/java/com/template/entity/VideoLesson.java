package com.template.entity;

import jakarta.persistence.*;

/**
 * PROJECT 2: E-Learning Platform
 * Stack: Spring Boot + Hibernate + Reactive (WebFlux) + MySQL + JUnit
 *
 * Features: Courses with video lessons, quizzes, progress tracking,
 *           instructor dashboards, certificate generation, reactive REST API.
 */
@Entity
@Table(name = "video_lesson")
public class VideoLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "duration_seconds")
    private int durationSeconds;

    @Column(name = "position_order")
    private int order;
}
