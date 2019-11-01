package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Atm {
    public static void main(String[] args) {
        SpringApplication.run(Atm.class, args);
/*        // read account file path from argument
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
        } while (true);*/
    }
}
