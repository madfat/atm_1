package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface AccountDao {
    /**
     * get all accounts
     *
     * @return list of account
     */
    List<Account> getAllAccount();

    /**
     * get detail of spesific account
     *
     * @param account
     * @param pin
     * @return Account detail
     */
    Account getLoginAccount(String account, String pin);

    /**
     * generate initial accounts randomly and write the generated account to csv file
     *
     * @param count
     */
    void generateRandomAccount(int count);

    void writeToFile(List<String[]> datalines);

}
