package src.cdc.atm.service;

import src.cdc.atm.model.Account;

import java.util.Arrays;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    private static AccountDaoImpl accountDaoInstance;
    static {
        accountDaoInstance = new AccountDaoImpl();
    }

    private AccountDaoImpl() {}

    public static AccountDaoImpl getInstance(){
        return accountDaoInstance;
    }

    @Override
    public List<Account> getAllAccount() {
        return Arrays.asList(
                new Account("John Doe", "012108", 60, "112233"),
                new Account("Jane Doe", "932012", 30,"112244")
        );
    }

    @Override
    public Account getLoginAccount(String account, String pin) {
        List<Account> accounts = getAllAccount();
        return accounts.stream()
                .filter(acct -> account.equalsIgnoreCase(acct.getAccountNumber()) && pin.equalsIgnoreCase(acct.getPin()))
                .findAny()
                .orElse(null);
    }
}
