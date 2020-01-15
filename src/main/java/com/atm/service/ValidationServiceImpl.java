package com.atm.service;

import com.atm.model.Account;
import com.atm.model.ErrorItem;
import com.atm.repository.AccountRepository;
import com.atm.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

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
        Account loginUser = authenticateUser();

        List<ErrorItem> errorItems = new ArrayList<>();
        if (loginUser != null){
            if (loginUser.getAccountNo().equals(dstAccountNo)){
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

    private Account authenticateUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account userDetail = null;
        if (principal instanceof UserDetails) {
            String loginUser = ((UserDetails) principal).getUsername();
            return userDetail = accountService.getAccountDetail(loginUser);
        } else {
            return (Account) principal;
        }
    }

    private boolean dstAmountNotExist(String dstAccountNo) {
        if (accountRepository.findByAccountNo(dstAccountNo) != null)
            return false;
        return true;
    }

    @Override
    public String withdrawalValidation(String srcAccountNo, Double trxAmount) {
        if (trxAmount > Constant.maxAmountTransfer) {
            return "Maximum amount to withdraw is $1000";
        }
        if (validateAccountAndBalance(srcAccountNo, trxAmount))
            return "Invalid account or balance is insufficient";
        return null;
    }

    private Boolean validateAccountAndBalance(String acctNo, Double trxAmount){
        Account acct = accountRepository.findByAccountNo(acctNo).orElseThrow(RuntimeException::new);
        Double balance = acct.getBalance() - trxAmount;
        // balance could not be less then or equal 0
        return balance <= 0 ? true : false;
    }

    private Boolean accountNotExist(String acctNo){
        Account acct = accountRepository.findByAccountNo(acctNo).orElse(null);
        return StringUtils.isEmpty(acct) ? true:false;
    }

}
