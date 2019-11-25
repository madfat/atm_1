package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;
import com.atm.utils.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.atm.utils.Constant.dft;
import static com.atm.utils.Constant.loginAccount;


@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public Transaction transferProcess(String srcAccount, String dstAccount, Double transactionAmount, String refNo) throws ValidationException {
        // source
        Account source = accountRepository.findByAccountNo(srcAccount);
        source.setBalance(source.getBalance() - transactionAmount);
        accountRepository.save(source);
        BeanUtils.copyProperties(source,loginAccount);
        // destination
        Account destination = accountRepository.findByAccountNo(dstAccount);
        destination.setBalance(destination.getBalance() + transactionAmount);
        accountRepository.save(destination);

        return transactionRepository.save(constructTransaction(source, destination, transactionAmount, Constant.TRX_TYPE.TF, refNo));
    }

    @Override
    @Transactional
    public Transaction withdrawProcess(String srcAccountNo, Double transactionAmount) {
        // validate source account
        Account sourceAccount = accountRepository.findByAccountNo(srcAccountNo);
        // update source account balance
        sourceAccount.setBalance(sourceAccount.getBalance() - transactionAmount);
        accountRepository.save(sourceAccount);
        // update login account information
        BeanUtils.copyProperties(sourceAccount,loginAccount);
        // create transaction history
        return transactionRepository.save(constructTransaction(sourceAccount, null, transactionAmount, Constant.TRX_TYPE.WD, null));
    }

    private Transaction constructTransaction(Account sourceAccount, Account destinationAccount, Double trxAmount, String type, String refNo) {
        Transaction trx = new Transaction();
        trx.setType(type);
        trx.setAmount(trxAmount);
        trx.setSourceAccount(sourceAccount.getAccountNo());
        trx.setTransactionDate(dft.format(LocalDateTime.now()));
        if (Constant.TRX_TYPE.TF.equals(type)) {
            trx.setRefNo(refNo);
            trx.setDestinationAccount(destinationAccount.getAccountNo());
        }
        return trx;
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

    @Override
    public String getRefNo() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    @Override
    public List<Transaction> getByDateRange(String startDate, String endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }
}
