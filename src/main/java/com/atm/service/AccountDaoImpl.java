package com.atm.service;

import com.atm.model.Account;
import com.atm.utils.Constant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class AccountDaoImpl extends CommonService implements AccountDao {
    private static final String[] HEADER = new String[]{"name","pin", "balance", "account_no"};

    private static AccountDaoImpl accountDaoInstance;
    static {
        accountDaoInstance = new AccountDaoImpl();
    }

    private AccountDaoImpl() {}

    public static AccountDaoImpl getInstance(){
        return accountDaoInstance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Account> getAllAccount() {
        List<Account> accounts = new ArrayList<>();
        try {
            File file = new File(Constant.ACCOUNT_FILE_PATH);
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            // skip the header of the csv
            accounts = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Function<String, Account> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines
        Account account = new Account();
        account.setName(p[0]);//<-- this is the first column in the csv file
        account.setPin(p[1]);
        account.setBalance(Double.valueOf(p[2]));
        account.setAccountNumber(p[3]);
        return account;
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public Account getLoginAccount(String account, String pin) {
        List<Account> accounts = getAllAccount();
        return accounts.stream()
                .filter(acct -> account.equalsIgnoreCase(acct.getAccountNumber()) && pin.equalsIgnoreCase(acct.getPin()))
                .findAny()
                .orElse(null);
    }

    @Override
    public void generateRandomAccount(int count) {
        // preparing the random accounts
        List<String[]> datalines = new ArrayList<>();
        for (int i=0;i<count;i++) {
            datalines.add(new String[]{"John Doe "+i, String.format("1122%02d",i),String.valueOf(getRandomNumberInRange(15,20)*100),String.format("9988%02d",i)});
        }

        writeToFile(datalines);
    }

    public void writeToFile(List<String[]> datalines) {
        File outputFile = new File(Constant.ACCOUNT_FILE_PATH);
        try (PrintWriter pw = new PrintWriter(outputFile)){
            pw.println(convertToCSV(HEADER));
            datalines.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account findAccountByAccountNo(String accountNo) {
        AccountDao accountDao = AccountDaoImpl.getInstance();
        List<Account> accounts = accountDao.getAllAccount();
        return accounts.stream()
                .filter(acct -> accountNo.equalsIgnoreCase(acct.getAccountNumber()))
                .findAny()
                .orElse(null);
    }

    private int getRandomNumberInRange(int min, int max){
        if (min >= max){
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }
}
