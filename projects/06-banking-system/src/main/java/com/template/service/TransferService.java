package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransferService {

    private final AccountRepository accountRepo;
    private final TransactionRepository transactionRepo;

    public TransferService(AccountRepository accountRepo,
                           TransactionRepository transactionRepo) {
        this.accountRepo     = accountRepo;
        this.transactionRepo = transactionRepo;
    }

    @Transactional
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account from = accountRepo.findByAccountNumberWithLock(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account to   = accountRepo.findByAccountNumberWithLock(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (from.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        String ref = UUID.randomUUID().toString();

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepo.save(from);
        accountRepo.save(to);

        saveTransaction(from, Transaction.TransactionType.TRANSFER_OUT, amount, from.getBalance(), ref);
        saveTransaction(to,   Transaction.TransactionType.TRANSFER_IN,  amount, to.getBalance(),   ref);
    }

    private void saveTransaction(Account account, Transaction.TransactionType type,
                                  BigDecimal amount, BigDecimal balanceAfter, String ref) {
        Transaction tx = new Transaction();
        tx.setAccount(account);
        tx.setType(type);
        tx.setAmount(amount);
        tx.setBalanceAfter(balanceAfter);
        tx.setTransactionDate(LocalDateTime.now());
        tx.setReferenceNumber(ref);
        transactionRepo.save(tx);
    }
}
