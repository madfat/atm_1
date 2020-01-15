package com.atm.repository;

import com.atm.model.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testFindByAccountNo_returnSuccess() {
        accountRepository.save(new Account("Albert", "123123","111111", 2000, true));
        Account act = accountRepository.findByAccountNo("123123").orElseThrow(RuntimeException::new);
        Assert.assertEquals(Double.valueOf(2000), act.getBalance(),0);
    }

    @Test
    public void testFindByAccountNo_returnException() {
        accountRepository.save(new Account("Albert", "123123","111111", 2000, true));
        try {
            Account act = accountRepository.findByAccountNo("444444").orElseThrow(() -> new UsernameNotFoundException("Account No not found"));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UsernameNotFoundException);
        }
    }

    @Test
    public void testFindByAccountNoAndPin_returnSuccess() {
        accountRepository.save(new Account("Albert", "123124","111131", 5000, true));
        Account act = accountRepository.findByAccountNoAndPin("123124", "111131");
        Assert.assertEquals(Double.valueOf(5000), act.getBalance(),0);
    }

    @Test
    public void testFindByAccountNoAndPin_returnNull() {
        accountRepository.save(new Account("Albert", "123124","111131", 5000, true));
        Account act = accountRepository.findByAccountNoAndPin("333333", "222222");
        Assert.assertNull(act);
    }

    @Test
    public void testFindAllAccount_returnSuccess() {
        accountRepository.save(new Account("Albert", "999999","121131", 5000, true));
        Iterable<Account> accounts = accountRepository.findAll();
        Account act = null;
        for (Account account : accounts) {
            if (account.getAccountNo().equals("999999"))
                act = account;
        }
        Assert.assertNotNull(act);
    }
}
