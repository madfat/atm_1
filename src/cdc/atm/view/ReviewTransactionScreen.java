package src.cdc.atm.view;

import src.cdc.atm.model.Transaction;
import src.cdc.atm.service.*;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static src.cdc.atm.utils.Constant.TRANSACTION_HEADER;

public class ReviewTransactionScreen extends CommonScreen implements CommonView {
    Scanner in = new Scanner(System.in);
    TransactionService transactionService = TransactionServiceImpl.getInstance();
    ValidationService validationService = ValidationServiceImpl.getInstance();
    AccountDao accountDao = AccountDaoImpl.getInstance();

    @Override
    public void show() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Transaction History");
        System.out.print("Enter the account number: ");
        String accountNo = in.nextLine();
        if (validationService.isAccountExist(accountNo)){
            System.out.println("--> Invalid account");
        } else {
            List<String[]> transactionList = transactionService.getTransactionList(accountNo);
            if (transactionList.isEmpty()) {
                System.out.println("**No transaction history**");
            } else {
                System.out.println("-------------------------------------------------------");
                System.out.println("Account No: " + accountNo);
                System.out.println("Total transaction: " + transactionList.size() + " records");
                System.out.println("Current balance: " + getCurrentBalance(accountNo) + "\n");
                System.out.println("transaction_date,type,source_acct,destination_acct,amount");
                for (String[] strings : transactionList) {
                    System.out.println(convertToCSV(strings));
                }
            }
        }
    }

    private String getCurrentBalance(String accountNo) {
        return String.valueOf(accountDao.findAccountByAccountNo(accountNo).getBalance());
    }

    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(String data) {
        if (data == null) return null;
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
