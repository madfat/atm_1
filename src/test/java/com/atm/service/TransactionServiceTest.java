package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    public void testTransferProcess_returnBalanceCalculationSuccess() {
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        String ref = "321321";
        Double tfAmount = Double.valueOf(50);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 3000, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 3000, true);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        transactionService.transferProcess(srcAcctNo,dstAcctNo, tfAmount, ref);

        Assert.assertEquals(3000 - tfAmount,srcAccount.getBalance(),0);
        Assert.assertEquals(3000 + tfAmount,dstAccount.getBalance(),0);
    }

    @Test
    public void testWithdrawProcess_returnSuccess() {
        String srcAcctNo = "123123";
        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 3000, true);
        Double withdrawAmount = Double.valueOf(40);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        transactionService.withdrawProcess(srcAcctNo, withdrawAmount);

        Assert.assertEquals(3000 - withdrawAmount, srcAccount.getBalance(),0);
    }
}
