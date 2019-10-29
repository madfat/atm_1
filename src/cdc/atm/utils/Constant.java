package src.cdc.atm.utils;

import src.cdc.atm.model.Account;

public class Constant {
    public static Account loginAccount = new Account();
    public static Account destinationAccount = new Account();
    public static final Double maxAmountTransfer = Double.valueOf(1000);
    public static final Double minAmountTransfer = Double.valueOf(1);

    public static void resetLoginAccount() {
        loginAccount.setBalance(0.0);
        loginAccount.setAccountNumber(null);
        loginAccount.setName(null);
        loginAccount.setPin(null);
    }
}
