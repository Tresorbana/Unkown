package com.template.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private InventoryItem item;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    private MovementType type;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "quantity_before")
    private int quantityBefore;

    @Column(name = "quantity_after")
    private int quantityAfter;

    @Column(name = "moved_at", nullable = false)
    private LocalDateTime movedAt;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(length = 255)
    private String notes;

    public enum MovementType { PURCHASE, SALE, ADJUSTMENT, TRANSFER, RETURN, WRITE_OFF }
}
