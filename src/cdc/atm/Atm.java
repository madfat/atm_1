package src.cdc.atm;

import src.cdc.atm.service.AccountDao;
import src.cdc.atm.service.AccountDaoImpl;
import src.cdc.atm.view.WelcomeScreen;

public class Atm {
    public static void main(String[] args) {
        // generate randomly 20 Accounts
        AccountDao accountDao = AccountDaoImpl.getInstance();
        accountDao.generateRandomAccount(20);

        WelcomeScreen welcomeScreen = new WelcomeScreen();
        do {
            welcomeScreen.show();
        } while (true);
    }
}
