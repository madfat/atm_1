package com.atm.service;

import com.atm.model.Account;
import com.atm.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@EnableJpaRepositories
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private ValidationService validationService;

    @Test
    public void testLogin_returnSuccess() {
        Account acct = new Account("Robert", "121314", "131313", 2000, true);
        when(accountRepository.findByAccountNoAndPin("121314", "131313")).thenReturn(acct);
        Account expectedAccount = accountService.login("121314", "131313");
        Assert.assertNotNull(expectedAccount);
    }

    @Test
    public void testGetAccountDetail_returnSuccess() {
        Account acct = new Account("Robert", "121314", "131313", 2000, true);
        when(accountRepository.findByAccountNo("121314")).thenReturn(java.util.Optional.ofNullable(acct));
        Account expectedResult = accountService.getAccountDetail("121314");
        Assert.assertNotNull(expectedResult);
    }

    @Test
    public void testGetAllAccount_returnSuccess() {
        Account act1 = new Account("Joe", "009988", "112222", 2000, true);
        Account act2 = new Account("Joe", "009980", "112222", 3000, true);
        Account act3 = new Account("Joe", "009981", "112222", 4000, true);
        Iterable<Account> accounts = new ArrayList<>();
        ((ArrayList<Account>) accounts).add(act1);
        ((ArrayList<Account>) accounts).add(act2);
        ((ArrayList<Account>) accounts).add(act3);

        when(accountRepository.findAll()).thenReturn(accounts);

        Iterable<Account> accountList = accountService.getAllAccount();

        int count = 0;
        for (Account account : accountList) {
            count +=1 ;
        }
        Assert.assertNotNull(accountList);
        Assert.assertEquals(3, count);
    }

}
