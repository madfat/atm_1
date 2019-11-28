package com.atm.service;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.repository.AccountRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.atm.utils.Constant.loginAccount;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ValidationServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private ValidationServiceImpl validationService;

    @Test
    public void testTransferValidation_returnDstAccountError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "123123";
        Double transferAmount = Double.valueOf(30);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 300, true);

        setLoginAccount(srcAcctNo, Double.valueOf(300));
        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(srcAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("dstAccount.same.srcAccount",errors.get(0).getErrorDesc());
    }

    @Test
    public void testTransferValidation_returnInsufficientError(){
        String srcAcctNo = "123123";
        String dstAcctNo = "112233";
        Double transferAmount = Double.valueOf(350);

        Account srcAccount = new Account("Andy", srcAcctNo, "111111", 300, true);
        Account dstAccount = new Account("Joe", dstAcctNo, "111112", 300, true);

        setLoginAccount(srcAcctNo, Double.valueOf(300));
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

        setLoginAccount(srcAcctNo, Double.valueOf(300));
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

        setLoginAccount(srcAcctNo, Double.valueOf(3000));
        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);

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

        setLoginAccount(srcAcctNo, Double.valueOf(3000));
        when(accountRepository.findByAccountNo(srcAcctNo)).thenReturn(srcAccount);
        when(accountRepository.findByAccountNo(dstAcctNo)).thenReturn(dstAccount);

        List<ErrorItem> errors = validationService.transferValidation(srcAcctNo, dstAcctNo, transferAmount);
        Assert.assertEquals(1, errors.size());
        Assert.assertEquals("transfer.min.amount",errors.get(0).getErrorDesc());
    }

    private void setLoginAccount(String srcAccount, Double balance) {
        loginAccount.setAccountNo(srcAccount);
        loginAccount.setBalance(balance);
        loginAccount.setName("Jole");
        loginAccount.setPin("332211");
        loginAccount.setStatus(true);

    }
}
