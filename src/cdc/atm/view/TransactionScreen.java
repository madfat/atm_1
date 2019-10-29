package src.cdc.atm.view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static src.cdc.atm.utils.Constant.loginAccount;

public class TransactionScreen extends CommonScreen implements CommonView {

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        Boolean onTransactionScreen = true;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("-------------------------------------------------------");
            for (String menu : getTransactionMenu()) {
                System.out.println(menu);
            }
            System.out.print("Please choose option[3]: ");
            String menuOption = in.nextLine();
            switch (menuOption){
                case "1":
                    WithdrawalScreen withdrawalScreen = (WithdrawalScreen) CommonScreen.getInstance(WithdrawalScreen.class);
                    withdrawalScreen.show(loginAccount);
                    break;
                case "2":
                    FundTransferScreen fundTransferScreen = (FundTransferScreen) CommonScreen.getInstance(FundTransferScreen.class);
                    fundTransferScreen.show();
                    break;
                case "3":
                    onTransactionScreen = false;
                    break;
                default:
                    if (menuOption.isEmpty()){
                        onTransactionScreen = false;
                    } else {
                        System.out.println("--> Invalid option");
                    }
            }
//            return cont;
        } while (onTransactionScreen);
    }

    private List<String> getTransactionMenu() {
        return Arrays.asList("1. Withdraw", "2. Fund Transfer", "3. Exit");
    }
}
