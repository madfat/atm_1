package com.atm.service;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.repository.AccountRepository;
import com.atm.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.ValidationUtils;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.atm.utils.Constant.loginAccount;

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
    public List<ErrorItem> transferValidation(String srcAccountNo, String dstAccountNo, Double trxAmount) {
        List<ErrorItem> errorItems = new ArrayList<>();
        if (loginAccount != null){
            if (loginAccount.getAccountNo().equals(dstAccountNo)){
                errorItems.add(new ErrorItem("dstAccountNo","dstAccount.same.srcAccount"));
            }
        }

        if (validateAccountAndBalance(srcAccountNo, trxAmount)){
            errorItems.add(new ErrorItem("srcAccountNo","srcAccount.amount.insufficient"));
        }
        if (accountNotExist(dstAccountNo)) {
            errorItems.add(new ErrorItem("dstAccountNo","account.invalid"));
        }

        if (trxAmount > Constant.maxAmountTransfer)
            errorItems.add(new ErrorItem("trxAmount", "transfer.max.amount"));

        if (trxAmount < Constant.minAmountTransfer)
            errorItems.add(new ErrorItem("trxAmount", "transfer.min.amount"));

        return errorItems;
    }

    private boolean dstAmountNotExist(String dstAccountNo) {
        if (accountRepository.findByAccountNo(dstAccountNo) != null)
            return false;
        return true;
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

    private Boolean accountNotExist(String acctNo){
        Account acct = accountRepository.findByAccountNo(acctNo);
        return StringUtils.isEmpty(acct) ? true:false;
    }

}
