package atm.view;

import atm.model.Account;
import atm.service.TransactionService;
import atm.service.TransactionServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WithdrawalScreen extends CommonScreen {

    public void show(Account loggedInAccount){
        TransactionService service = TransactionServiceImpl.getInstance();
        OtherWithdrawalScreen otherWithdralScreen = new OtherWithdrawalScreen();

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
                    // withdraw by 10
                    deductAmount = Double.valueOf(10);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    break;
                case "2":
                    // withdraw by 50
                    deductAmount = Double.valueOf(50);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    break;
                case "3":
                    // withdraw by 100
                    deductAmount = Double.valueOf(100);
                    service.calcWithdrawalBalance(loggedInAccount, deductAmount);
                    break;
                case "4":
                    // display other withdraw screen
                    otherWithdralScreen.show();
                    break;
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
