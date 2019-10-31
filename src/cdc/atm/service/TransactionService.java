package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface TransactionService {
    /**
     * Calculate balance for transfer process
     *
     * @param sourceAccount
     * @param destinationAccount
     * @param transactionAmount
     */
    void calcTransferBalance(Account sourceAccount, Account destinationAccount, Double transactionAmount);

    /**
     * Calculate balance for withdrawal balance
     *
     * @param sourceAccout
     * @param transactionAmount
     */
    void calcWithdrawalBalance(Account sourceAccout, Double transactionAmount);

    /**
     * Retrieve all the transactions record for specific account no
     * @param accountNumber
     * @return List of lines of transactions
     */
    List<String[]> getTransactionList(String accountNumber);
}
