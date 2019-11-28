package com.atm.service;

import com.atm.model.Account;
import com.atm.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {
    private Account savedAccount = null;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountServiceImpl accountService;

    @Before
    public void setup() {
        Account acct = new Account("Robert","121314", "131313", 2000, true);
        when(accountRepository.findByAccountNoAndPin("121314","131313")).thenReturn(acct);
    }

    @Test
    public void testLogin_returnSuccess(){
        Account expectedAccount = accountService.login("121314","131313");
        Assert.assertEquals(expectedAccount, savedAccount);
    }

    @Test
    public void testGetAccountDetail_returnSuccess() {
        Account expectedResult = accountService.getAccountDetail("121314");
        Assert.assertEquals(expectedResult, savedAccount);
    }

}
