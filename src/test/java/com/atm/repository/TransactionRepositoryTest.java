package com.atm.repository;

import com.atm.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@RunWith(SpringRunner.class)
public class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testFindTransactionWithinRangeDate_returnSuccess() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction trx1 = new Transaction(LocalDateTime.of(2019,12,02,10,18,29), "Withdraw", "112233", null, Double.valueOf(10), null);
        Transaction trx2 = new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null);
        Transaction trx3 = new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112233", null, Double.valueOf(30), null);
        transactions.add(trx1);
        transactions.add(trx2);
        transactions.add(trx3);

        transactionRepository.saveAll(transactions);

        List<Transaction> trxs = transactionRepository.findBySourceAccountOrDestinationAccountAndTransactionDateBetweenOrderByTransactionDateDesc("112233","112233",LocalDateTime.of(2019,12,01, 10,18,29), LocalDateTime.of(2019,12,02,10,18,29));
        Assert.assertEquals(3, trxs.size());
    }

    @Test
    public void testFindTop10Transaction_returnSuccess() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(LocalDateTime.of(2019,12,02,10,18,29), "Withdraw", "112233", null, Double.valueOf(10), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112233", null, Double.valueOf(30), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,02,10,18,29), "Withdraw", "112233", null, Double.valueOf(10), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112233", null, Double.valueOf(30), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,05,10,18,29), "Withdraw", "112233", null, Double.valueOf(10), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112233", null, Double.valueOf(30), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,02,10,18,29), "Withdraw", "112244", "112233", Double.valueOf(10), "019182"));
        transactions.add(new Transaction(LocalDateTime.of(2019,12,01,10,18,29), "Withdraw", "112233", null, Double.valueOf(20), null));
        transactions.add(new Transaction(LocalDateTime.of(2019,11,30,10,18,29), "Withdraw", "112244", "112233", Double.valueOf(30), "223394"));
        transactionRepository.saveAll(transactions);

        List<Transaction> top10 = transactionRepository.findTop10BySourceAccountOrDestinationAccountOrderByTransactionDateDesc("112233", "112233");
        Assert.assertEquals(10, top10.size());
        Assert.assertEquals(LocalDateTime.of(2019,12,05, 10,18,29), top10.get(0).getTransactionDate());
    }
}
