package atm;

import atm.service.AccountDao;
import atm.service.AccountDaoImpl;
import atm.utils.Constant;
import atm.view.WelcomeScreen;

public class Atm {
    public static void main(String[] args) {
        // read account file path from argument
        if (args.length > 0){
            Constant.ACCOUNT_FILE_PATH = args [0];
        } else {
            Constant.ACCOUNT_FILE_PATH = "./data/account_data.csv";
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
