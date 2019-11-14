package src.cdc.atm.view;

import java.util.Random;
import java.util.Scanner;

public class FundTransferSummaryScreen extends CommonScreen {
    public void show(String trxAmount) {
        String refNo = getRandomNumberToString();
        FundTransferGenerateRefNoScreen fundTransferGenerateRefNoScreen = (FundTransferGenerateRefNoScreen) CommonScreen.getInstance(FundTransferGenerateRefNoScreen.class);
        fundTransferGenerateRefNoScreen.show(refNo);

        FundTransferConfirmationScreen fundTransferConfirmationScreen = (FundTransferConfirmationScreen) CommonScreen.getInstance(FundTransferConfirmationScreen.class);
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
