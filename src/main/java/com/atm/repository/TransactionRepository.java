package com.atm.repository;

import com.atm.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, String> {
    List<Transaction> findBySourceAccountAndTransactionDateBetweenOrDestinationAccountAndTransactionDateBetweenOrderByTransactionDateDesc(String srcAccount, LocalDateTime startDate1, LocalDateTime endDate1, String dstAccount, LocalDateTime startDate2, LocalDateTime endDate2);
    List<Transaction> findTop10BySourceAccountOrDestinationAccountOrderByTransactionDateDesc(String srcAccount, String dstAccount);
    Page<Transaction> findBySourceAccountOrDestinationAccountOrderByTransactionDateDesc(String srcAccount, String dstAccount, Pageable pageable);
}
