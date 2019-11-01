package com.atm.view;

import static com.atm.utils.Constant.destinationAccount;
import static com.atm.utils.Constant.loginAccount;

public class FundTransferReceiptScreen extends CommonScreen {
    public void show(String trxAmount, String refNo) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Fund Transfer Summary");
        StringBuilder summary  = new StringBuilder();
        summary.append("Destination Account: " + destinationAccount.getAccountNumber() + "\n");
        summary.append("Transfer amount: $" + trxAmount + "\n");
        summary.append("Reference Number: " + refNo + "\n");
        summary.append("Balance: $" + loginAccount.getBalance() + "\n\n");
        System.out.print(summary);
    }
}
