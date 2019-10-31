package atm.service;

import atm.model.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static atm.utils.Constant.TRANSACTION_FILE_PATH;
import static atm.utils.Constant.TRANSACTION_HEADER;


public class TransactionDaoImpl extends CommonService implements TransactionDao {

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
                pw.println(convertToCSV(TRANSACTION_HEADER));
            }
            pw.println(convertToCSV(mapToLine(transaction)));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            File file = new File(TRANSACTION_FILE_PATH);
            InputStream input = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            // skip the header of the csv
            transactions = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Function<String, Transaction> mapToItem = (line) -> {
        String[] p = line.split(",");// a CSV has comma separated lines
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(p[0]);//<-- this is the first column in the csv file
        transaction.setType(p[1]);
        transaction.setSourceAccount(p[2]);
        transaction.setDestinationAccount(p[3]);
        transaction.setAmount(Double.valueOf(p[4]));
        return transaction;
    };

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
}
