package com.atm.view;

import com.atm.utils.Constant;

import java.util.Date;
import java.util.List;

public class SummaryScreen extends CommonScreen {

    public void show(Double transactionAmount, List<String> errors) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Summary");
        System.out.println(String.format("Date: %s", new Date()));
        System.out.println(String.format("Withdraw: %s", transactionAmount));
        System.out.println(String.format("Balance: $%s", Constant.loginAccount.getBalance()));
    }
}
