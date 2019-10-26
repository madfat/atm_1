package src.cdc.atm.view;

import java.util.List;

public class CommonScreen {
    private static WithdrawalScreen withdrawalScreenIntance;
    private static SummaryScreen summaryScreenInstance;
    private static TransactionScreen transactionScreenInstance;
    private static OtherWithdrawalScreen otherWithdrawalScreenInstance;
    private static FundTransferScreen fundTransferScreenInstance;
    private static FundTransferSummaryScreen fundTransferSummaryScreenInstance;
    private static FundTransferGenerateRefNoScreen fundTransferGenerateRefNoScreenInstance;
    private static FundTransferConfirmationScreen fundTransferConfirmationScreenInstance;
    private static FundTransferReceiptScreen fundTransferReceiptScreenInstance;

    protected void displayError(List<String> errors, Double transactionAmount){
        SummaryScreen summaryScreen = (SummaryScreen) getInstance(SummaryScreen.class);
        if (errors.isEmpty()) {
            summaryScreen.show(transactionAmount, null);
            return;
        }
        for (String error : errors) {
            System.out.println("--> " + error);
        }
        summaryScreen.show(transactionAmount, errors);

    }

    public static CommonScreen getInstance(Class clazz) {
        try {
            switch (clazz.getSimpleName()){
                case "WithdrawalScreen":
                    if (withdrawalScreenIntance == null) {
                        withdrawalScreenIntance = (WithdrawalScreen) clazz.newInstance();
                    }
                    return withdrawalScreenIntance;
                case "SummaryScreen":
                    if (summaryScreenInstance == null) {
                        summaryScreenInstance = (SummaryScreen) clazz.newInstance();
                    }
                    return summaryScreenInstance;
                case "TransactionScreen":
                    if (transactionScreenInstance == null) {
                        transactionScreenInstance = (TransactionScreen) clazz.newInstance();
                    }
                    return  transactionScreenInstance;
                case "OtherWithdrawalScreen":
                    if (otherWithdrawalScreenInstance == null) {
                        otherWithdrawalScreenInstance = (OtherWithdrawalScreen) clazz.newInstance();
                    }
                    return otherWithdrawalScreenInstance;
                case "FundTransferScreen":
                    if (fundTransferScreenInstance == null) {
                        fundTransferScreenInstance = (FundTransferScreen) clazz.newInstance();
                    }
                    return fundTransferScreenInstance;
                case "FundTransferSummaryScreen":
                    if (fundTransferSummaryScreenInstance == null) {
                        fundTransferSummaryScreenInstance = (FundTransferSummaryScreen) clazz.newInstance();
                    }
                    return fundTransferSummaryScreenInstance;
                case "FundTransferGenerateRefNoScreen":
                    if (fundTransferGenerateRefNoScreenInstance == null) {
                        fundTransferGenerateRefNoScreenInstance = (FundTransferGenerateRefNoScreen) clazz.newInstance();
                    }
                    return fundTransferGenerateRefNoScreenInstance;
                case "FundTransferConfirmationScreen":
                    if (fundTransferConfirmationScreenInstance == null){
                        fundTransferConfirmationScreenInstance = (FundTransferConfirmationScreen) clazz.newInstance();
                    }
                    return fundTransferConfirmationScreenInstance;
                case "FundTransferReceiptScreen":
                    if (fundTransferReceiptScreenInstance == null) {
                        fundTransferReceiptScreenInstance = (FundTransferReceiptScreen) clazz.newInstance();
                    }
                    return fundTransferReceiptScreenInstance;
                default:
                        //
                }
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
