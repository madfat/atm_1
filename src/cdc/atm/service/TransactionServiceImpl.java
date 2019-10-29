package src.cdc.atm.service;

import src.cdc.atm.model.Account;
import src.cdc.atm.view.CommonScreen;
import src.cdc.atm.view.SummaryScreen;

import java.util.ArrayList;
import java.util.List;

import static src.cdc.atm.utils.Constant.destinationAccount;
import static src.cdc.atm.utils.Constant.loginAccount;

public class TransactionServiceImpl implements TransactionService {
    private static TransactionServiceImpl transactionServiceInstance;

    static {
        transactionServiceInstance = new TransactionServiceImpl();
    }

    private TransactionServiceImpl(){

    }

    public static TransactionServiceImpl getInstance(){
        return transactionServiceInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calcTransferBalance(Account sourceAccount, Account destAccount, Double transactionAmount) {
        loginAccount.setBalance(loginAccount.getBalance() - transactionAmount);
        destinationAccount.setBalance(destAccount.getBalance() + transactionAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calcWithdrawalBalance(Account sourceAccout, Double transactionAmount) {
        List<String> errors = new ArrayList<>();
        Double result = sourceAccout.getBalance() - transactionAmount;
        if (result < 0) {
            errors.add("--> Insufficient balance");
        } else {
            loginAccount.setBalance(result);
        }
        displayError(errors, transactionAmount);
    }

    protected void displayError(List<String> errors, Double transactionAmount){
        SummaryScreen summaryScreen = (SummaryScreen) CommonScreen.getInstance(SummaryScreen.class);
        if (errors.isEmpty()) {
            summaryScreen.show(transactionAmount, null);
            return;
        }
        for (String error : errors) {
            System.out.println(error);
        }
        summaryScreen.show(transactionAmount, errors);
    }
}
