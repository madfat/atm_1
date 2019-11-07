package com.atm.service;

import com.atm.model.Account;
import com.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidationService validationService;

    @Override
    public Account login(String accountNo, String pin) {
        return accountRepository.findByAccountNoAndPin(accountNo,pin);
    }
}
