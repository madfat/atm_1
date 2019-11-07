package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void transferProcess(String srcAccount, String dstAccount, Double transactionAmount) throws ValidationException {
        validationService.transferValidation(srcAccount,dstAccount,transactionAmount);

    }

    @Override
    public void withdrawProcess(String srcAccountNo, Double transactionAmount) {
        // validate source account

        // update source account balance
        // create transaction history
    }

    private void validate(String srcAccountNo, Double transactionAmount) {
        accountRepository.findByAccountNo(srcAccountNo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Transaction> getTransactionList(String accountNumber) {
        return null; //transactionRepository.findTop10ByAccountNo(accountNumber);
    }
}
