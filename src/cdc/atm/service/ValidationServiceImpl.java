package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static src.cdc.atm.utils.Constant.*;

public class ValidationServiceImpl implements ValidationService {
    private static ValidationServiceImpl validationServiceInstance;

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
        if (deductAmount > maxAmountTransfer) {
            System.out.println(String.format("--> Maximum amount to withdraw is $%s", maxAmountTransfer));
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
            if (Double.valueOf(transactionAmount) > maxAmountTransfer) errors.add(String.format("--> Maximum amount to transfer is $%s", maxAmountTransfer));
            if (Double.valueOf(transactionAmount) < minAmountTransfer) errors.add(String.format("--> Minimum amount to withdraw is $%s", minAmountTransfer));
            if (isBalanceNotSufficient(loginAccount,Double.valueOf(transactionAmount)) &&
                    Double.valueOf(transactionAmount) <= maxAmountTransfer) errors.add(String.format("--> Insufficient balance $%s", transactionAmount));
        }

        return errors;
    }

    private boolean destinationNotAccountExist(String destinationAccountNo) {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        List<Account> accounts = accountDao.getAllAccount();
        Account account = accounts.stream()
                .filter(acct -> destinationAccountNo.equalsIgnoreCase(acct.getAccountNumber()))
                .findAny()
                .orElse(null);
        if (!Objects.isNull(account)) {
            destinationAccount.setBalance(account.getBalance());
            destinationAccount.setName(account.getName());
            destinationAccount.setPin(account.getPin());
            destinationAccount.setAccountNumber(account.getAccountNumber());
            return false;
        }
        return true;
    }

    private static boolean isBalanceNotSufficient(Account loggedInAccount, Double deductAmount) {
        Double result = loggedInAccount.getBalance() - deductAmount;
        return result < 0 ? true:false;
    }
}
