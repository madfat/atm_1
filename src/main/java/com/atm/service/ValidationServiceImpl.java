package com.atm.service;

import com.atm.exception.AtmValidationException;
import com.atm.model.Account;
import com.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.bind.ValidationException;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(String account, String pin) throws ValidationException {
        if (account.length() != 6) throw new ValidationException("Account number should have 6 digit length");
        if (!account.matches("-?\\d+(\\.\\d+)?")) throw new ValidationException("Account Number should only contains numbers");
        // pin validation
        if (pin.length() != 6) throw new ValidationException("PIN Number should have 6 digit length");
        if (!pin.matches("-?\\d+(\\.\\d+)?")) throw new ValidationException("PIN Number should only contains numbers");
    }

    @Override
    public void transferValidation(String srcAccountNo, String dstAccountNo, Double trxAmount) throws ValidationException {
        AtmValidationException ave = new AtmValidationException();
        if (validateAccountAndBalance(srcAccountNo, trxAmount)){
            throw new ValidationException("Invalid account or balance is insufficient");
        }
        if (!isAccountExist(dstAccountNo)) {
            throw new ValidationException("Invalid account");
        }
    }

    @Override
    public void withdrawalValidation(String srcAccountNo, Double trxAmount) {
        //
    }

    private Boolean validateAccountAndBalance(String acctNo, Double trxAmount){
        Account acct = accountRepository.findByAccountNo(acctNo);
        if (!StringUtils.isEmpty(acct)) {
            Double balance = acct.getBalance() - trxAmount;
            // balance could not be less then or equal 0
            if (balance <= 0){
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private Boolean isAccountExist(String acctNo){
        Account acct = accountRepository.findByAccountNo(acctNo);
        return StringUtils.isEmpty(acct) ? false:true;
    }

}
