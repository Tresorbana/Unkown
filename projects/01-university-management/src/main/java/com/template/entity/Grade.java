package com.template.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "grade",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id", "term", "year"}))
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false, length = 20)
    private String term;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private double score;

    @Column(length = 5)
    private String letterGrade;
}
