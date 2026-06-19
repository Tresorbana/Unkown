package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * PROJECT 15: Event Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit + Reactive
 *
 * Features: Events, Venues, Ticket types, Registrations, Speaker management,
 *           QR code check-in, Payments, Feedback surveys, Analytics dashboard.
 */
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @Column(name = "banner_url")
    private String bannerUrl;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketType> ticketTypes;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    private List<Registration> registrations;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_speakers",
               joinColumns        = @JoinColumn(name = "event_id"),
               inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;

    public enum EventStatus { DRAFT, PUBLISHED, CANCELLED, COMPLETED }
}
