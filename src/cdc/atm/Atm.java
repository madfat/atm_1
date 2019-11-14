package src.cdc.atm;

import src.cdc.atm.service.AccountDao;
import src.cdc.atm.service.AccountDaoImpl;
import src.cdc.atm.view.WelcomeScreen;

import java.io.FileWriter;
import java.io.IOException;

import static src.cdc.atm.utils.Constant.ACCOUNT_FILE_PATH;
import static src.cdc.atm.utils.Constant.TRANSACTION_FILE_PATH;

public class Atm {
    public static void main(String[] args) throws IOException {
        // reset the content of transaction file
        new FileWriter(TRANSACTION_FILE_PATH, false).close();
        // read account file path from argument
        if (args.length > 0){
            ACCOUNT_FILE_PATH = args [0];
        } else {
            ACCOUNT_FILE_PATH = "./data/account_data.csv";
        }

        // generate randomly 20 Accounts
        AccountDao accountDao = AccountDaoImpl.getInstance();
        accountDao.generateRandomAccount(20);

        WelcomeScreen welcomeScreen = new WelcomeScreen();
        do {
            welcomeScreen.show();
        } while (true);
    }
}
