package com.atm.utils;

import com.atm.model.Account;

import java.text.SimpleDateFormat;

public class Constant {
    public static Account loginAccount = new Account();
    public static Account destinationAccount = new Account();
    public static final Double maxAmountTransfer = Double.valueOf(1000);
    public static final Double minAmountTransfer = Double.valueOf(1);
    public static String ACCOUNT_FILE_PATH = "";
    public static final String TRANSACTION_FILE_PATH = "./data/transaction_data.csv";
    public static final String[] TRANSACTION_HEADER = new String[] {"transaction_date", "type", "source_acct", "destination_acct", "amount", "balance"};
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd:mm-ss");

    public static void resetLoginAccount() {
        loginAccount.setBalance(0.0);
        loginAccount.setAccountNo(null);
        loginAccount.setName(null);
        loginAccount.setPin(null);
    }

    public final class TRX_TYPE {
        public static final String TF = "Transfer";
        public static final String WD = "Withdraw";
    }
}
