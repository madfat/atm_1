package src.cdc.atm.service;

import src.cdc.atm.model.Transaction;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TransactionDaoImpl implements TransactionDao {
    private static final String TRANSACTION_FILE_PATH = "./data/transaction_data.csv";
    private static final String[] HEADER = new String[] {"transaction_date", "type", "source_acct", "destination_acct", "amount", "balance"};

    private static TransactionDaoImpl transactionDaoInstance;
    static {
        transactionDaoInstance = new TransactionDaoImpl();
    }

    public static TransactionDaoImpl getInstance(){
        return  transactionDaoInstance;
    }

    @Override
    public String save(Transaction transaction) {
        try (FileWriter fw = new FileWriter(TRANSACTION_FILE_PATH, true)) {
            PrintWriter pw = new PrintWriter(fw);
            if (!isHeaderExist()) {
                pw.println(convertToCSV(HEADER));
            }
            pw.println(convertToCSV(mapToLine(transaction)));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    private Boolean isHeaderExist() {
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            if (br.readLine() == null) return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private String[] mapToLine(Transaction trx) {
        return new String[]{trx.getTransactionDate(), trx.getType(), trx.getSourceAccount(), trx.getDestinationAccount(), String.valueOf(trx.getAmount()), String.valueOf(trx.getBalance())};
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
