package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * PROJECT 11: Inventory Management System
 * Stack: Spring Boot + Hibernate + MySQL + JSP + Servlets + JUnit
 *
 * Features: Products, Suppliers, Stock levels, Purchase orders,
 *           Low-stock alerts, Warehouse locations, Audit trail.
 */
@Entity
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku", unique = true, nullable = false, length = 50)
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity_on_hand")
    private int quantityOnHand;

    @Column(name = "reorder_level")
    private int reorderLevel;

    @Column(name = "unit_cost", precision = 10, scale = 2)
    private BigDecimal unitCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_location_id")
    private WarehouseLocation location;

    public boolean isLowStock() {
        return quantityOnHand <= reorderLevel;
    }
}
