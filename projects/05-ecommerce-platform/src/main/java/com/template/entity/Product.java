package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * PROJECT 5: E-Commerce Platform
 * Stack: Spring Boot + Hibernate + MySQL + Reactive (WebFlux) + Spring Security + JUnit
 *
 * Features: Product catalogue, Shopping cart, Orders, Payments,
 *           Inventory, Reviews, Admin dashboard, Seller portal.
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;
}
