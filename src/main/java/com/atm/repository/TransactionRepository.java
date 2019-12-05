package com.atm.repository;

import com.atm.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    List<Transaction> findByTransactionDateBetweenOrderByTransactionDateDesc(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> findTop10BySourceAccountOrDestinationAccountOrderByTransactionDateDesc(String srcAccount, String dstAccount);
}
