package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * PROJECT 10: Hotel Booking System
 * Stack: Spring Boot + Hibernate + MySQL + Spring Security + JUnit + Reactive
 *
 * Features: Room catalogue, Availability calendar, Booking, Check-in/out,
 *           Housekeeping schedule, Guest profile, Revenue reports.
 */
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", unique = true, nullable = false, length = 10)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Column
    private int capacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public enum RoomType   { SINGLE, DOUBLE, TWIN, SUITE, DELUXE }
    public enum RoomStatus { AVAILABLE, OCCUPIED, MAINTENANCE, RESERVED }
}
