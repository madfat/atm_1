import java.util.*;

public class App {
    public static void main(String[] args) {
        Boolean exitApp = false;
        Boolean logged = false;
        Account loggedInAccount = new Account();
        Account destinationAccount = new Account();
        Scanner in = new Scanner(System.in);
        while (!exitApp) {
            boolean valid = true;
            if (!logged) {
                valid = welcomeScreen(valid, in, logged, loggedInAccount);
            }
            if (valid) {
                transactionScreen(in, exitApp,loggedInAccount, destinationAccount);
            }
        } ;

        in.close();
    }

    private static Boolean welcomeScreen(Boolean valid, Scanner in, boolean logged, Account loggedInAccount) {
        System.out.println("-------------------------------------------------------");
        System.out.print("Enter Account Number: ");
        String account = in.nextLine();
        System.out.print("Enter PIN: ");
        String pin = in.nextLine();
        List<String> errors = new ArrayList<>();
        errors = validation(account, pin, errors);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println(error);
            }
            valid = false;
        }
        // copy to global object
        Account acct = getLoginAccount(account, pin, loggedInAccount);
        Boolean isAccountFound = Objects.isNull(acct) ? false:true;
        if (isAccountFound){
            loggedInAccount.setBalance(acct.getBalance());
            loggedInAccount.setAccountNumber(acct.getAccountNumber());
            loggedInAccount.setName(acct.getName());
            loggedInAccount.setPin(acct.getPin());
        }

        if (valid && !isAccountFound){
            System.out.println("Invalid Account Number/PIN");
            valid = false;
            logged = true;
        }
        return valid;
    }

    private static void transactionScreen(Scanner in, Boolean exitApp, Account loggedInAccount, Account destinationAccount) {
        System.out.println("-------------------------------------------------------");
        for (String menu : getMenu()) {
            System.out.println(menu);
        }
        System.out.print("Please choose option[3]: ");
        String menuOption = in.nextLine();
        switch (menuOption){
            case "1":
                withdrawalScreen(in, exitApp, loggedInAccount, destinationAccount);
                break;
            case "2":
                fundTransferScreen(in, exitApp, loggedInAccount, destinationAccount);
                break;
            case "3":
                exitApp = true;
                break;
            default:
                if (menuOption.isEmpty()){
                    exitApp = true;
                } else {
                    transactionScreen(in, false,loggedInAccount,destinationAccount);
                }
        }
    }

    private static void fundTransferScreen(Scanner in, Boolean exitApp, Account loggedInAccount, Account destinationAccount) {
        System.out.println("-------------------------------------------------------");
        System.out.print("Please enter destination account and press enter to continue \nor pres enter to go back to Transaction: ");
        String fundDestination = in.nextLine();
        if (fundDestination.isEmpty()) {
            transactionScreen(in, exitApp, loggedInAccount, destinationAccount);
            return;
        }
        System.out.print("Please enter transfer amount \nand press enter to continue \nor press enter to go back to Transaction: ");
        String fundAmount = in.nextLine();
        if (fundDestination.isEmpty()) {
            transactionScreen(in, exitApp, loggedInAccount, destinationAccount);
            return;
        }
        List<String> errors = validateAccount(loggedInAccount, fundDestination, fundAmount, destinationAccount);
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println(error);
            }
            transactionScreen(in, false, loggedInAccount, destinationAccount);
        }
        String refNo = getRandomNumberToString();
        System.out.println(String.format("Reference Number: %s", refNo));
        System.out.println("Press enter to continue");
        in.nextLine();
        StringBuilder summary = new StringBuilder();
        summary.append("Transfer Confirmation \n");
        summary.append("Destination Account: " + fundDestination + "\n");
        summary.append("Transfer Amount: " + fundAmount +"\n");
        summary.append("Reference Number: " + refNo);
        summary.append("\n\n");
        transferConfirmationScreen(in, summary, loggedInAccount, destinationAccount, Double.valueOf(fundAmount), refNo);
    }

    private static List<String> validateAccount(Account loggedInAccount, String fundDestination, String fundAmount, Account destinationAccount) {
        Double maxToTransfer = Double.valueOf(1000);
        Double minToTransfer = Double.valueOf(1);

        List<String> errors = new ArrayList<>();
        if (!fundAmount.matches("-?\\d+(\\.\\d+)?") ||
                destinationNotAccountExist(fundDestination, destinationAccount)) {
            errors.add("Invalid account");
        } else {
            if (Double.valueOf(fundAmount) > maxToTransfer) errors.add(String.format("Maximum amount to transfer is $%s",maxToTransfer));
            if (Double.valueOf(fundAmount) < minToTransfer) errors.add(String.format("Minimum amount to withdraw is $%s", minToTransfer));
            if (isBalanceNotSufficient(loggedInAccount,Double.valueOf(fundAmount)) &&
                    Double.valueOf(fundAmount) <= maxToTransfer) errors.add(String.format("Insufficient balance $%s", fundAmount));
        }

        return errors;
    }

    private static boolean destinationNotAccountExist(String fundDestination, Account destinationAccount) {
        List<Account> accounts = getAccount();
        Account account = accounts.stream()
                .filter(acct -> fundDestination.equalsIgnoreCase(acct.getAccountNumber()))
                .findAny()
                .orElse(null);
        if (!Objects.isNull(account)) {
            destinationAccount.setBalance(account.getBalance());
            destinationAccount.setName(account.getName());
            destinationAccount.setPin(account.getPin());
            destinationAccount.setAccountNumber(account.getAccountNumber());
            return false;
        }
        return true;
    }

    private static void transferConfirmationScreen(Scanner in, StringBuilder summary, Account loggedInAccount, Account destinationAccount, Double fundAmount, String refNo) {
        System.out.println("-------------------------------------------------------");
        summary.append("1. Confirm Trx \n2. Cancel Trx");
        System.out.println(summary);
        System.out.print("Choose option[2]: ");
        String selectedTransferConfirmation = in.nextLine();
        switch (selectedTransferConfirmation){
            case "1":
                // confirm fund transfer
                loggedInAccount.setBalance(loggedInAccount.getBalance() - fundAmount);
                destinationAccount.setBalance(destinationAccount.getBalance() + fundAmount);
                fundTransferSummaryScreen(in, loggedInAccount,destinationAccount,fundAmount,refNo);
                break;
            case "2":
                // cancel fund transfer
                break;
            default:
                // cancel fund transfer
        }
    }

    private static void fundTransferSummaryScreen(Scanner in, Account loggedInAccount, Account destinationAccount, Double fundAmount, String refNo) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Fund Transfer Summary");
        StringBuilder summary  = new StringBuilder();
        summary.append("Destination Account: " + destinationAccount.getAccountNumber() + "\n");
        summary.append("Transfer amount: $" + fundAmount + "\n");
        summary.append("Reference Number: " + refNo + "\n");
        summary.append("Balance: $" + loggedInAccount.getBalance() + "\n\n");
        System.out.print(summary);
        for (String menu : getSummaryMenu()) {
            System.out.println(menu);
        }
        System.out.print("Choose option[2]: ");
        String selectedSummaryMenu = in.nextLine();
        switch (selectedSummaryMenu){
            case "1":
                transactionScreen(in, false,loggedInAccount, destinationAccount);
                break;
            case "2":
                break;
            default:
        }
    }

    private static void withdrawalScreen(Scanner in, Boolean exitApp, Account loggedInAccount, Account destinationAccount) {
        System.out.println("-------------------------------------------------------");
        List<String> withdrawMenu = Arrays.asList("1. $10","2. $50","3. $100","4. Other","5. Back");
        for (String menu : withdrawMenu) {
            System.out.println(menu);
        }
        System.out.print("Please choose option[5]: ");
        String selectedWithdrawMenu = in.nextLine();
        Double deductAmount = Double.valueOf(0);
        switch (selectedWithdrawMenu){
            case "1":
                deductAmount = Double.valueOf(10);
                // deduct user balance with 10
                calculateBalance(in,loggedInAccount, deductAmount, destinationAccount);
                break;
            case "2":
                deductAmount = Double.valueOf(50);
                // deduct user balance with 50
                calculateBalance(in, loggedInAccount, deductAmount, destinationAccount);
                break;
            case "3":
                deductAmount = Double.valueOf(100);
                // deduct user balance with 100
                calculateBalance(in, loggedInAccount, deductAmount, destinationAccount);
                break;
            case "4":
                // display other withdraw screen
                otherWithdrawalScreen(in, loggedInAccount, destinationAccount);
            case "5":
                // back to transaction screen
                transactionScreen(in, exitApp, loggedInAccount, destinationAccount);
                break;
            default:
                // back to transaction screen
                transactionScreen(in, exitApp, loggedInAccount, destinationAccount);
        }
    }

    private static void otherWithdrawalScreen(Scanner in, Account loggedInAccount, Account destinationAccount) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Other Withdrawal");
        System.out.print("Enter amount to withdraw: ");
        try {
            Double deductAmount = Double.valueOf(in.nextLine());
            if (validAmount(in, loggedInAccount, deductAmount)){
                calculateBalance(in,loggedInAccount,deductAmount, destinationAccount);
            }
        } catch (Exception e) {
            System.out.println("Invalid amount");
        }

    }

    private static Boolean validAmount(Scanner in, Account loggedInAccount, Double deductAmount) {
        Double maxAmount = Double.valueOf(1000);
        Boolean valid = true;
        if (deductAmount > maxAmount) {
            System.out.println(String.format("Maximum amount to withdraw is $%s", maxAmount));
            valid = false;
        } else if (deductAmount % 10 != 0) {
            System.out.println("Invalid amount");
            valid = false;
        } else if (isBalanceNotSufficient(loggedInAccount, deductAmount)){
            System.out.println(String.format("Insufficient balance $%s", deductAmount));
            valid = false;
        }
        return valid;
    }

    private static boolean isBalanceNotSufficient(Account loggedInAccount, Double deductAmount) {
        Double result = loggedInAccount.getBalance() - deductAmount;
        return result < 0 ? true:false;
    }

    private static void summaryScreen(Scanner in, Account loggedInAccount, Double deductAmount, Boolean exitApp, Account destinationAccount) {
        System.out.println("-------------------------------------------------------");
        System.out.println("Summary");
        System.out.println(String.format("Date: %s", new Date()));
        System.out.println(String.format("Withdraw: %s", deductAmount));
        System.out.println(String.format("Balance: $%s", loggedInAccount.getBalance()));
        for (String menu : getSummaryMenu()) {
            System.out.println(menu);
        }
        System.out.print("Choose option[2]: ");
        String selectedSummaryMenu = in.nextLine();
        switch (selectedSummaryMenu) {
            case "1":
                transactionScreen(in, false, loggedInAccount, destinationAccount);
                break;
            case "2":
                exitApp = true;
                break;
            default:
                exitApp = true;
        }
    }

    private static void calculateBalance(Scanner in, Account loggedInAccount, Double deduction, Account destinationAccount) {
        Double result = loggedInAccount.getBalance() - deduction;
        if (result < 0) {
            System.out.println("Insufficient balance");
            withdrawalScreen(in,false,loggedInAccount, destinationAccount);
        } else {
            loggedInAccount.setBalance(result);
            // go to summary screen
            summaryScreen(in, loggedInAccount, deduction, false, destinationAccount);
        }
    }

    private static String getRandomNumberToString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }

    private static Account getLoginAccount(String account, String pin, Account loggedInAccount) {
        List<Account> accounts = getAccount();
        return accounts.stream()
                .filter(acct -> account.equalsIgnoreCase(acct.getAccountNumber()) && pin.equalsIgnoreCase(acct.getPin()))
                .findAny()
                .orElse(null);
    }

    private static List<String> validation(String account, String pin, List<String> errors) {
        // account validation
        if (account.length() != 6) errors.add("Account Number should have 6 digit length");
        if (!account.matches("-?\\d+(\\.\\d+)?")) errors.add("Account Number should only contains numbers");
        // pin validation
        if (pin.length() != 6) errors.add("PIN Number should have 6 digit length");
        if (!pin.matches("-?\\d+(\\.\\d+)?")) errors.add("PIN Number should only contains numbers");
        return errors;
    }

    private static List<Account> getAccount(){
        return Arrays.asList(
                new Account("John Doe", "012108", 100, "112233"),
                new Account("Jane Doe", "932012", 30,"112244")
        );
    }
    private static List<String> getMenu() {
        return Arrays.asList("1. Withdraw", "2. Fund Transfer", "3. Exit");
    }

    private static List<String> getSummaryMenu() {
        return Arrays.asList("1. Transaction", "2. Exit");
    }

}
