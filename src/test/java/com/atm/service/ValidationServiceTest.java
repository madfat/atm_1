package com.atm.service;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ValidationServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Test
    public void testTransferValidation_returnDstAccountError(){
        mockAuth();

        String srcAcctNo = "123123";
        String dstAcctNo = "123123";
        Double transferAmount = Double.valueOf(30);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 300, true);
        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(srcAccount);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(srcAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("dstAccount.same.srcAccount",errors.get(0).getErrorDesc());
    }

    private void mockAuth() {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testTransferValidation_returnInsufficientError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        Double transferAmount = Double.valueOf(350);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 300, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 300, true);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("srcAccount.amount.insufficient",errors.get(0).getErrorDesc());
    }

    @Test
    public void testTransferValidation_returnDstAccountNotExistError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        Double transferAmount = Double.valueOf(30);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 300, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 300, true);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("account.invalid",errors.get(0).getErrorDesc());
    }

    @Test
    public void testTransferValidation_returnMaxAmountTransferError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        Double transferAmount = Double.valueOf(1001);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 3000, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 3000, true);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);
        mockAuth();
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(srcAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("transfer.max.amount",errors.get(0).getErrorDesc());
    }

    @Test
    public void testTransferValidation_returnMinAmountTransferError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        Double transferAmount = Double.valueOf(0);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 3000, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 3000, true);

        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("transfer.min.amount",errors.get(0).getErrorDesc());
    }

}
