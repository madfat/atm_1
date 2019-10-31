package src.cdc.atm.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static src.cdc.atm.utils.Constant.loginAccount;

public class SummaryScreen extends CommonScreen {

    public void show(Double transactionAmount, List<String> errors) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Summary");
        System.out.println(String.format("Date: %s", new Date()));
        System.out.println(String.format("Withdraw: %s", transactionAmount));
        System.out.println(String.format("Balance: $%s", loginAccount.getBalance()));
    }
}
