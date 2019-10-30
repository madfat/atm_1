package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface ValidationService {
    /**
     * Validate logging account
     *
     * @param account
     * @param pin
     * @return List of error if errors occur
     */
    List<String> login(String account, String pin);

    /**
     * Validate account prior withdrawal process
     * @param loginAccount
     * @param transactionAmount
     * @return boolean of account validity
     */
    Boolean validAmount(Account loginAccount, Double transactionAmount);

    /**
     * Validate account prior transfer process
     *
     * @param destinationAccountNo
     * @param transactionAmount
     * @return boolean of account validity
     */
    List<String> validateAccount(String destinationAccountNo, String transactionAmount);

    /**
     * Validate whether the passed account is exist or not
     *
     * @param accountNo
     * @return Boolean
     */
    Boolean isAccountExist(String accountNo);
}
