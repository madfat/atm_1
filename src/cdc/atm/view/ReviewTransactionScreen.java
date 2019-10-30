package src.cdc.atm.view;

import src.cdc.atm.model.Transaction;
import src.cdc.atm.service.TransactionService;
import src.cdc.atm.service.TransactionServiceImpl;
import src.cdc.atm.service.ValidationService;
import src.cdc.atm.service.ValidationServiceImpl;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReviewTransactionScreen extends CommonScreen implements CommonView {
    Scanner in = new Scanner(System.in);
    TransactionService transactionService = TransactionServiceImpl.getInstance();
    ValidationService validationService = ValidationServiceImpl.getInstance();
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
                System.out.println("Total transaction: " + transactionList.size() + " records\n");
                for (String[] strings : transactionList) {
                    System.out.println(convertToCSV(strings));
                }
            }
        }
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
