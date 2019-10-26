package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface ValidationService {
    List<String> login(String account, String pin);
    Boolean validAmount(Account loginAccount, Double transactionAmount);
    List<String> validateAccount(String destinationAccountNo, String transactionAmount);
}
