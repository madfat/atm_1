package src.cdc.atm.service;

import src.cdc.atm.model.Transaction;

import java.util.List;

public interface TransactionDao {
    /**
     * save transaction record to csv
     *
     * @param transaction
     * @return "success"
     */
    String save(Transaction transaction);

    /**
     * Get all transaction from csv file
     *
     * @return list of transaction records
     */
    List<Transaction> getAllTransaction();
}
