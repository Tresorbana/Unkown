package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * PROJECT 3: Library Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Books, Members, Borrowing/Returns, Overdue fines,
 *           Reservations, Catalogue search, Librarian portal.
 */
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @Column
    private String publisher;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "total_copies")
    private int totalCopies;

    @Column(name = "available_copies")
    private int availableCopies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private BookCategory category;
}
