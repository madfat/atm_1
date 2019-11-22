package com.atm.service;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.ValidationUtils;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private AccountRepository accountRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ErrorItem> login(String account, String pin) {
        List<ErrorItem> errors = new ArrayList<>();

        if (account.length() != 6)
            errors.add(new ErrorItem("accountNo", "loginParameter.accountNo.length"));

        if (!account.matches("-?\\d+(\\.\\d+)?"))
            errors.add(new ErrorItem("accountNo", "loginParameter.accountNo.type"));

        // pin validation
        if (pin.length() != 6)
            errors.add(new ErrorItem("pin", "loginParameter.pin.length"));
        if (!pin.matches("-?\\d+(\\.\\d+)?"))
            errors.add(new ErrorItem("pin", "loginParameter.pin.type"));

        return errors;
    }

    @Override
    public void transferValidation(String srcAccountNo, String dstAccountNo, Double trxAmount) throws ValidationException {
        if (validateAccountAndBalance(srcAccountNo, trxAmount)){
            throw new ValidationException("Invalid account or balance is insufficient");
        }
        if (!isAccountExist(dstAccountNo)) {
            throw new ValidationException("Invalid account");
        }
    }

    @Override
    public String withdrawalValidation(String srcAccountNo, Double trxAmount) {
        if (validateAccountAndBalance(srcAccountNo, trxAmount))
            return "Invalid account or balance is insufficient";
        return null;
    }

    private Boolean validateAccountAndBalance(String acctNo, Double trxAmount){
        Account acct = accountRepository.findByAccountNo(acctNo);
        if (!StringUtils.isEmpty(acct)) {
            Double balance = acct.getBalance() - trxAmount;
            // balance could not be less then or equal 0
            if (balance <= 0){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private Boolean isAccountExist(String acctNo){
        Account acct = accountRepository.findByAccountNo(acctNo);
        return !StringUtils.isEmpty(acct) ? true:true;
    }

}
