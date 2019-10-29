package src.cdc.atm.view;

import src.cdc.atm.service.TransactionService;
import src.cdc.atm.service.TransactionServiceImpl;

import java.util.Scanner;

import static src.cdc.atm.utils.Constant.destinationAccount;
import static src.cdc.atm.utils.Constant.loginAccount;

public class FundTransferConfirmationScreen extends CommonScreen {
    Scanner in = new Scanner(System.in);

    public void show(Double trxAmount, String refNo) {
        TransactionService transactionService = TransactionServiceImpl.getInstance();
        FundTransferReceiptScreen fundTransferReceiptScreen = new FundTransferReceiptScreen();

        Boolean transferConfirmationScreen = true;
        do {
            System.out.println("-------------------------------------------------------");
            StringBuilder summary = new StringBuilder();
            summary.append("Transfer Confirmation \n");
            summary.append("Destination Account: " + destinationAccount.getAccountNumber() + "\n");
            summary.append("Transfer Amount: " + trxAmount + "\n");
            summary.append("Reference Number: " + refNo);
            summary.append("\n\n");
            System.out.print(summary);
            System.out.println("1. Confirm Trx \n2. Cancel Trx");
            System.out.print("Choose option[2]: ");
            String selectedTransferConfirmation = in.nextLine();
            switch (selectedTransferConfirmation) {
                case "1":
                    transactionService.calcTransferBalance(loginAccount, destinationAccount, trxAmount);

                    fundTransferReceiptScreen.show(String.valueOf(trxAmount), refNo);
                    transferConfirmationScreen=false;
                    break;
                case "2":
                    // cancel fund transfer
                    transferConfirmationScreen=false;
                    break;
                default:
                    if (!selectedTransferConfirmation.isEmpty()) {
                        System.out.println("--> Invalid option");
                    }
                    // cancel fund transfer
            }
        }while (transferConfirmationScreen);
    }
 }
