package src.cdc.atm.view;

import src.cdc.atm.model.Account;
import src.cdc.atm.service.TransactionService;
import src.cdc.atm.service.TransactionServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WithdrawalScreen extends CommonScreen {

    public void show(Account loggedInAccount){
        TransactionService service = TransactionServiceImpl.getInstance();
        OtherWithdrawalScreen otherWithdralScreen = new OtherWithdrawalScreen();
        SummaryScreen summaryScreen = (SummaryScreen) CommonScreen.getInstance(SummaryScreen.class);
        Boolean onWithdrawalScreen = true;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("-------------------------------------------------------");
            List<String> withdrawMenu = Arrays.asList("1. $10","2. $50","3. $100","4. Other","5. Back");
            for (String menu : withdrawMenu) {
                System.out.println(menu);
            }
            System.out.print("Please choose option[5]: ");
            String selectedWithdrawMenu = in.nextLine();
            Double deductAmount = Double.valueOf(0);
            switch (selectedWithdrawMenu) {
                case "1":
                    deductAmount = Double.valueOf(10);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    summaryScreen.show(deductAmount, null);
                    break;
                case "2":
                    deductAmount = Double.valueOf(50);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    summaryScreen.show(deductAmount, null);
                    break;
                case "3":
                    deductAmount = Double.valueOf(100);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    summaryScreen.show(deductAmount, null);
                    summaryScreen.show(deductAmount, null);
                    break;
                case "4":
                    // display other withdraw screen
                    otherWithdralScreen.show();
                case "5":
                    // back to transaction screen
                    onWithdrawalScreen = false;
                    break;
                default:
                    if (selectedWithdrawMenu.isEmpty()) {
                        onWithdrawalScreen = false;
                    } else {
                        System.out.println("--> Invalid option");
                    }
            }
        } while (onWithdrawalScreen);
    }

}
