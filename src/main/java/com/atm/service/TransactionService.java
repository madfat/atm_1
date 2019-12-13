package com.atm.service;

import com.atm.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    /**
     * Transfer process, included transaction history creation
     *
     * @param srcAccount
     * @param dstAccount
     * @param transactionAmount
     * @return
     * @throws ValidationException
     */
    Transaction transferProcess(String srcAccount, String dstAccount, Double transactionAmount, String refNo);

    /**
     * process for withdrawal
     *
     * @param srcAccountNo
     * @param transactionAmount
     */
    Transaction withdrawProcess(String srcAccountNo, Double transactionAmount);

    /**
     * Retrieve all the transactions record for specific account no
     * @param accountNumber
     * @return List of lines of transactions
     */
    List<Transaction> getTransactionList(String accountNumber);

    /**
     * Generate reference no
     *
     * @return
     */
    String getRefNo();

    List<Transaction> getByDateRange(String accountNo, LocalDateTime startDate, LocalDateTime endDate);

    Page<Transaction> getAllTransactionList(String accountNo, Pageable pageable);
}
