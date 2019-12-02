package com.atm.repository;

import com.atm.model.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        Transaction trx1 = new Transaction("2019-12-02 10:18:29", "Withdraw", "112233", null, Double.valueOf(10), null);
        Transaction trx2 = new Transaction("2019-12-01 10:18:29", "Withdraw", "112233", null, Double.valueOf(20), null);
        Transaction trx3 = new Transaction("2019-11-30 10:18:29", "Withdraw", "112233", null, Double.valueOf(30), null);
        transactions.add(trx1);
        transactions.add(trx2);
        transactions.add(trx3);

        for (Transaction transaction : transactions) {
            transactionRepository.save(transaction);
        }

        List<Transaction> trxs = transactionRepository.findByTransactionDateBetween("2019-12-01 10:18:29", "2019-12-02 10:18:29");
        Assert.assertEquals(2, trxs.size());
    }
}
