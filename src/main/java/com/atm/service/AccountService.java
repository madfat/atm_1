package com.atm.service;

import com.atm.model.Account;

public interface AccountService {
    Account login(String accountNo, String pin);
    Account getAccountDetail(String accountNo);
    Iterable<Account> getAllAccount();
}
