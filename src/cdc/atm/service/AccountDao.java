package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.List;

public interface AccountDao {
    List<Account> getAllAccount();
    Account getLoginAccount(String account, String pin);
}
