package com.atm.utils;

import com.atm.model.Account;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;

public class Constant {
    public static Account loginAccount = new Account();
    public static final Double maxAmountTransfer = Double.valueOf(1000);
    public static final Double minAmountTransfer = Double.valueOf(1);public static DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter dft_notime = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

    public final class TRX_TYPE {
        public static final String TF = "Transfer";
        public static final String WD = "Withdraw";
    }
}
