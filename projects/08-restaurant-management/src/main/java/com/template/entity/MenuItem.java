package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * PROJECT 8: Restaurant Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Menu management, Table reservations, Order taking,
 *           Kitchen queue, Billing, Inventory, Staff scheduling.
 */
@Entity
@Table(name = "menu_item")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal price;

    @Column(name = "preparation_time_minutes")
    private int preparationTimeMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private MenuCategory category;

    @Column(name = "is_available")
    private boolean available;

    @Column(name = "image_url")
    private String imageUrl;
}
