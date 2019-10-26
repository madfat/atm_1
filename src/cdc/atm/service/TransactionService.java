package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface TransactionService {
    void calcTransferBalance(Account sourceAccount, Account destinationAccount, Double transactionAmount);
    void calcWithdrawalBalance(Account sourceAccout, Double transactionAmount);
}
