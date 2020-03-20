package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.repository.AccountRepository;
import com.atm.repository.TransactionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TransactionServiceTest {
    @Mock
    private ValidationService validationService;

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

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(java.util.Optional.ofNullable(srcAccount));
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(java.util.Optional.ofNullable(dstAccount));
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

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(java.util.Optional.ofNullable(srcAccount));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());
        transactionService.withdrawProcess(srcAcctNo, withdrawAmount);

        Assert.assertEquals(3000 - withdrawAmount, srcAccount.getBalance(),0);
    }

    @Test
    public void testGetTransactionWithinRangeDate_returnSuccess() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction trx1 = new Transaction(LocalDateTime.of(2019,12,02,10,18,29), "Withdraw", "112233", null, Double.valueOf(10), null);
        Transaction trx2 = new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null);
        Transaction trx3 = new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112233", null, Double.valueOf(30), null);
        transactions.add(trx1);
        transactions.add(trx2);
        transactions.add(trx3);

        when(transactionRepository.findBySourceAccountAndTransactionDateBetweenOrDestinationAccountAndTransactionDateBetweenOrderByTransactionDateDesc(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyString(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(transactions);

        List<Transaction> trxList = transactionService.getByDateRange("112233",LocalDateTime.of(2019,11,30,00,00,00), LocalDateTime.of(2019,12,02, 10,18,29));
        Assert.assertEquals(3,trxList.size());
    }
}
