package com.atm.view;

import com.atm.service.TransactionServiceImpl;

import java.util.Scanner;

import static com.atm.utils.Constant.loginAccount;

public class OtherWithdrawalScreen extends CommonScreen implements CommonView {
    TransactionServiceImpl transactionService = TransactionServiceImpl.getInstance();

    /**
     * {@inheritDoc}
     */
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
