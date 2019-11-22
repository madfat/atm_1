package com.atm.repository;

import com.atm.model.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
    List<Transaction> findByTransactionDateBetween(String startDate, String endDate);
}
