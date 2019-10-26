package src.cdc.atm.view;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static src.cdc.atm.utils.Constant.destinationAccount;
import static src.cdc.atm.utils.Constant.loginAccount;

public class SummaryScreen extends CommonScreen {

    public void show(Double transactionAmount, List<String> errors) {
        Boolean onSummaryScreen = true;
        do {
            if (errors == null) {
                System.out.println("-------------------------------------------------------");
                System.out.println("Summary");
                System.out.println(String.format("Date: %s", new Date()));
                System.out.println(String.format("Withdraw: %s", transactionAmount));
                System.out.println(String.format("Balance: $%s", loginAccount.getBalance()));
                System.out.print("\n");
            } else {
                System.out.println("-------------------------------------------------------");
            }

            for (String menu : getSummaryMenu()) {
                System.out.println(menu);
            }
            System.out.print("Choose option[2]: ");
            Scanner in = new Scanner(System.in);
            String selectedSummaryMenu = in.nextLine();

            switch (selectedSummaryMenu) {
                case "1":
                    TransactionScreen transactionScreen = (TransactionScreen) CommonScreen.getInstance(TransactionScreen.class);
                    transactionScreen.show();
                    break;
                case "2":
                    onSummaryScreen = false;
                    break;
                default:
                    if (!selectedSummaryMenu.isEmpty()) {
                        System.out.println("--> Invalid option");
                    } else {
                        onSummaryScreen = false;
                    }
            }
        } while (onSummaryScreen);
    }

    private static List<String> getSummaryMenu() {
        return Arrays.asList("1. Transaction", "2. Exit");
    }

}
