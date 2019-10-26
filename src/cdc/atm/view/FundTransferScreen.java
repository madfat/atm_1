package src.cdc.atm.view;

import src.cdc.atm.service.ValidationServiceImpl;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FundTransferScreen extends CommonScreen implements CommonView {
    Scanner in = new Scanner(System.in);
    TransactionScreen transactionScreen = (TransactionScreen) CommonScreen.getInstance(TransactionScreen.class);
    ValidationServiceImpl validationService = ValidationServiceImpl.getInstance();

    @Override
    public void show() {
        System.out.println("-------------------------------------------------------");
        System.out.print("Please enter destination account and press enter to continue \nor pres enter to go back to Transaction menu: ");
        String destinationAccountNo = in.nextLine();
        if (destinationAccountNo.isEmpty()) {
            transactionScreen.show();
            return;
        }
        System.out.print("\nPlease enter transfer amount \nand press enter to continue \nor press enter to go back to Transaction menu: ");
        String trxAmount = in.nextLine();
        if (trxAmount.isEmpty()) {
            transactionScreen.show();
            return;
        }
        List<String> errors = validationService.validateAccount(destinationAccountNo, trxAmount);
        if (!errors.isEmpty()) {
            System.out.print("\n");
            for (String error : errors) {
                System.out.println(error);
            }
            return;
        }

        FundTransferSummaryScreen fundTransferSummaryScreen = (FundTransferSummaryScreen) CommonScreen.getInstance(FundTransferSummaryScreen.class);
        fundTransferSummaryScreen.show(trxAmount);

    }
}
