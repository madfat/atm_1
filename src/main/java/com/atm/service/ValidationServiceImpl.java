package com.atm.service;

import com.atm.model.Account;
import com.atm.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidationServiceImpl implements ValidationService {
    private static ValidationServiceImpl validationServiceInstance;
    private static AccountDao accountDao = AccountDaoImpl.getInstance();

    static {
        validationServiceInstance = new ValidationServiceImpl();
    }

    private ValidationServiceImpl(){

    }

    public static ValidationServiceImpl getInstance(){
        return validationServiceInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> login(String account, String pin) {
        List<String> errors = new ArrayList<>();
        if (account.length() != 6) errors.add("--> Account Number should have 6 digit length");
        if (!account.matches("-?\\d+(\\.\\d+)?")) errors.add("--> Account Number should only contains numbers");
        // pin validation
        if (pin.length() != 6) errors.add("--> PIN Number should have 6 digit length");
        if (!pin.matches("-?\\d+(\\.\\d+)?")) errors.add("--> PIN Number should only contains numbers");
        return errors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean validAmount(Account loggedInAccount, Double deductAmount) {
        Boolean valid = true;
        if (deductAmount > Constant.maxAmountTransfer) {
            System.out.println(String.format("--> Maximum amount to withdraw is $%s", Constant.maxAmountTransfer));
            valid = false;
        } else if (deductAmount % 10 != 0) {
            System.out.println("--> Invalid amount");
            valid = false;
        } else if (isBalanceNotSufficient(loggedInAccount, deductAmount)){
            System.out.println(String.format("--> Insufficient balance $%s", deductAmount));
            valid = false;
        }
        return valid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> validateAccount(String destinationAccountNo, String transactionAmount) {
        List<String> errors = new ArrayList<>();
        if (destinationNotAccountExist(destinationAccountNo)) {
            errors.add("--> Invalid account");
        }

        if (!transactionAmount.matches("-?\\d+(\\.\\d+)?")){
            errors.add("Transfer amount is invalid");
        } else {
            if (Double.valueOf(transactionAmount) > Constant.maxAmountTransfer) errors.add(String.format("--> Maximum amount to transfer is $%s", Constant.maxAmountTransfer));
            if (Double.valueOf(transactionAmount) < Constant.minAmountTransfer) errors.add(String.format("--> Minimum amount to withdraw is $%s", Constant.minAmountTransfer));
            if (isBalanceNotSufficient(Constant.loginAccount,Double.valueOf(transactionAmount)) &&
                    Double.valueOf(transactionAmount) <= Constant.maxAmountTransfer) errors.add(String.format("--> Insufficient balance $%s", transactionAmount));
        }

        return errors;
    }

    @Override
    public Boolean isAccountExist(String accountNo) {
        return accountDao.findAccountByAccountNo(accountNo) != null ? false : true;
    }

    private boolean destinationNotAccountExist(String destinationAccountNo) {
        Account account = accountDao.findAccountByAccountNo(destinationAccountNo);
        if (!Objects.isNull(account)) {
            Constant.destinationAccount.setBalance(account.getBalance());
            Constant.destinationAccount.setName(account.getName());
            Constant.destinationAccount.setPin(account.getPin());
            Constant.destinationAccount.setAccountNumber(account.getAccountNumber());
            return false;
        }
        return true;
    }

    private static boolean isBalanceNotSufficient(Account loggedInAccount, Double deductAmount) {
        Double result = loggedInAccount.getBalance() - deductAmount;
        return result < 0 ? true:false;
    }
}
