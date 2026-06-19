package com.template.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EnrolmentId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "term", nullable = false, length = 20)
    private String term;

    @Column(name = "year", nullable = false)
    private int year;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EnrolmentId that)) return false;
        return year == that.year
                && Objects.equals(student, that.student)
                && Objects.equals(course, that.course)
                && Objects.equals(term, that.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course, term, year);
    }
}
