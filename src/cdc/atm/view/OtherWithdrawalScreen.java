package src.cdc.atm.view;

import src.cdc.atm.service.TransactionServiceImpl;
import src.cdc.atm.service.ValidationServiceImpl;

import java.util.Scanner;

import static src.cdc.atm.utils.Constant.loginAccount;

public class OtherWithdrawalScreen extends CommonScreen implements CommonView {
    ValidationServiceImpl validationService = ValidationServiceImpl.getInstance();
    TransactionServiceImpl transactionService = TransactionServiceImpl.getInstance();

    @Override
    public void show() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Other Withdrawal");
        System.out.print("Enter amount to withdraw: ");
        try {
            Scanner in = new Scanner(System.in);
            Double deductAmount = Double.valueOf(in.nextLine());

            transactionService.calcWithdrawalBalance(loginAccount, deductAmount);

        } catch (Exception e) {
            System.out.println("Invalid amount");
        }
    }
}
