package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "registration",
       uniqueConstraints = @UniqueConstraint(columnNames = {"attendee_id", "ticket_type_id"}))
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendee_id", nullable = false)
    private Attendee attendee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    @Column(name = "qr_code", unique = true, nullable = false)
    private String qrCode;

    @Column(name = "registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name = "checked_in")
    private boolean checkedIn;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @PrePersist
    void generateQrCode() {
        this.qrCode = UUID.randomUUID().toString();
        this.registeredAt = LocalDateTime.now();
    }

    public enum RegistrationStatus { CONFIRMED, WAITLISTED, CANCELLED }
}
