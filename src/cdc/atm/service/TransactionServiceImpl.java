package src.cdc.atm.service;

import src.cdc.atm.model.Account;
import src.cdc.atm.model.Transaction;
import src.cdc.atm.utils.Constant;
import src.cdc.atm.view.CommonScreen;
import src.cdc.atm.view.SummaryScreen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static src.cdc.atm.utils.Constant.destinationAccount;
import static src.cdc.atm.utils.Constant.loginAccount;
import static src.cdc.atm.utils.Constant.sdf;

public class TransactionServiceImpl implements TransactionService {
    private static TransactionServiceImpl transactionServiceInstance;
    TransactionDao transactionDao = TransactionDaoImpl.getInstance();
    AccountDao accountDao = AccountDaoImpl.getInstance();

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
        // update account file
        updateAccountData(loginAccount, destinationAccount);
        // update transaction file
        transactionDao.save(constructTransaction(transactionAmount, Constant.TRX_TYPE.TF));
    }

    private Transaction constructTransaction(Double transactionAmount, String type) {
        return new Transaction(sdf.format(new Date()), type, loginAccount.getAccountNumber(), getDestinationAccount(type), transactionAmount, loginAccount.getBalance());
    }

    private String getDestinationAccount(String type) {
        return type.equals(Constant.TRX_TYPE.TF)? destinationAccount.getAccountNumber():null;
    }

    private void updateAccountData(Account loginAccount, Account destinationAccount) {
        List<Account> oldAccounts = accountDao.getAllAccount();
        oldAccounts.stream().forEach(u -> {
            if (u.getAccountNumber().equals(loginAccount.getAccountNumber())){
                u.setBalance(loginAccount.getBalance());
            } else if (destinationAccount != null &&
                    u.getAccountNumber().equals(destinationAccount.getAccountNumber())){
                u.setBalance(destinationAccount.getBalance());
            }
        });

        accountDao.writeToFile(mapAccountToLine(oldAccounts));
    }

    private List<String[]> mapAccountToLine(List<Account> oldAccounts) {
        List<String[]> lines = new ArrayList<>();
        for (Account oldAccount : oldAccounts) {
            lines.add(new String[]{oldAccount.getName(), oldAccount.getPin(), String.valueOf(oldAccount.getBalance()), oldAccount.getAccountNumber()});
        }
        return lines;
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
            // update account file
            updateAccountData(loginAccount, null);
            // update transaction file
            transactionDao.save(constructTransaction(transactionAmount, Constant.TRX_TYPE.WD));
        }
        displayError(errors, transactionAmount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String[]> getTransactionList(String accountNumber) {
        List<Transaction> transactions = transactionDao.getAllTransaction();
        transactions = transactions.stream()
                .filter(byAccountNo(accountNumber))
                .collect(Collectors.toList());

        List<String[]> lines = new ArrayList<>();
        for (Transaction trx : transactions) {
             lines.add(new String[]{trx.getTransactionDate(), trx.getType(), trx.getSourceAccount(), trx.getDestinationAccount(), String.valueOf(trx.getAmount())});
        }
        return lines;
    }

    private static Predicate<Transaction> byAccountNo(String accountNo){
        if (loginAccount.getAccountNumber().equals(accountNo)) {
            return p -> p.getSourceAccount().equals(accountNo);
        }
        return p -> p.getDestinationAccount().equals(accountNo) && p.getType().equals(Constant.TRX_TYPE.TF);
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
    }
}
