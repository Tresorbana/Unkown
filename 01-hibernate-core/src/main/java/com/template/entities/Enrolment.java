package com.template.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    // Convenience accessors delegate to the composite key
    public Student getStudent() { return id.getStudent(); }
    public Course getCourse()   { return id.getCourse(); }
    public String getTerm()     { return id.getTerm(); }
    public int    getYear()     { return id.getYear(); }
}
