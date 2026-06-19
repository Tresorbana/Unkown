package com.template.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * PROJECT 6: Banking System
 * Stack: Spring Boot + Hibernate + MySQL + Spring Security + JUnit + Reactive
 *
 * Features: Accounts (savings/current), Transactions, Transfers,
 *           Loans, Interest calculation, Statements, ATM simulation.
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private BankCustomer customer;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public enum AccountType { SAVINGS, CURRENT, FIXED_DEPOSIT }
}
