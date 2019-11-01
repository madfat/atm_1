package com.atm.view;

import java.util.Random;
import java.util.Scanner;

public class FundTransferSummaryScreen extends CommonScreen {
    Scanner in = new Scanner(System.in);

    public void show(String trxAmount) {
        String refNo = getRandomNumberToString();
        FundTransferGenerateRefNoScreen fundTransferGenerateRefNoScreen = (FundTransferGenerateRefNoScreen) getInstance(FundTransferGenerateRefNoScreen.class);
        fundTransferGenerateRefNoScreen.show(refNo);

        FundTransferConfirmationScreen fundTransferConfirmationScreen = (FundTransferConfirmationScreen) getInstance(FundTransferConfirmationScreen.class);
        fundTransferConfirmationScreen.show(Double.valueOf(trxAmount),refNo);
    }

    private static String getRandomNumberToString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
