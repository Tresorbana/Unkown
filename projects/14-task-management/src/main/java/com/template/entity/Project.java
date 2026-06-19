package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * PROJECT 14: Task Management System (Jira/Trello-like)
 * Stack: Spring Boot + Hibernate + MySQL + Reactive (WebFlux) + Spring Security + JUnit
 *
 * Features: Projects, Boards, Sprints, Tasks, Sub-tasks, Labels,
 *           Assignments, Comments, Attachments, Activity timeline.
 */
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private TeamMember owner;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "project_members",
               joinColumns        = @JoinColumn(name = "project_id"),
               inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<TeamMember> members;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Board> boards;

    public enum ProjectStatus { PLANNING, ACTIVE, ON_HOLD, COMPLETED, CANCELLED }
}
