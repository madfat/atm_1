package src.cdc.atm.service;

import src.cdc.atm.model.Transaction;

public interface TransactionDao {
    String save(Transaction transaction);
}
