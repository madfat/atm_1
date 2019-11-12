package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface TransactionService {

    /**
     * process for transfer
     *
     * @param srcAccount
     * @param dstAccount
     * @param transactionAmount
     */
    void transferProcess(String srcAccount, String dstAccount, Double transactionAmount) throws ValidationException;

    /**
     * process for withdrawal
     *
     * @param srcAccountNo
     * @param transactionAmount
     */
    void withdrawProcess(String srcAccountNo, Double transactionAmount);

    /**
     * Retrieve all the transactions record for specific account no
     * @param accountNumber
     * @return List of lines of transactions
     */
    List<Transaction> getTransactionList(String accountNumber);
}
