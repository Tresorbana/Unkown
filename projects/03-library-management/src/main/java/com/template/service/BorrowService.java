package com.template.service;

import com.template.entity.*;
import com.template.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class BorrowService {

    private static final int LOAN_DAYS      = 14;
    private static final double FINE_PER_DAY = 0.50;

    private final BorrowRecordRepository borrowRepo;
    private final BookRepository bookRepo;

    public BorrowService(BorrowRecordRepository borrowRepo, BookRepository bookRepo) {
        this.borrowRepo = borrowRepo;
        this.bookRepo   = bookRepo;
    }

    public BorrowRecord borrowBook(Member member, Long bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if (book.getAvailableCopies() < 1) {
            throw new IllegalStateException("No copies available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(LOAN_DAYS));
        record.setStatus(BorrowRecord.BorrowStatus.BORROWED);
        return borrowRepo.save(record);
    }

    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRepo.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Borrow record not found"));

        LocalDate today = LocalDate.now();
        record.setReturnDate(today);

        if (today.isAfter(record.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(record.getDueDate(), today);
            record.setFineAmount(overdueDays * FINE_PER_DAY);
            record.setStatus(BorrowRecord.BorrowStatus.OVERDUE);
        } else {
            record.setStatus(BorrowRecord.BorrowStatus.RETURNED);
        }

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        return borrowRepo.save(record);
    }
}
