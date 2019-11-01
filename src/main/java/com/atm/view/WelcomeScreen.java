package com.atm.view;

import com.atm.model.Account;
import com.atm.service.AccountDao;
import com.atm.service.AccountDaoImpl;
import com.atm.service.ValidationService;
import com.atm.service.ValidationServiceImpl;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.atm.utils.Constant.loginAccount;
import static com.atm.utils.Constant.resetLoginAccount;

public class WelcomeScreen implements CommonView {
    Scanner in = new Scanner(System.in);
    ValidationService validation = ValidationServiceImpl.getInstance();
    TransactionScreen transactionScreen = new TransactionScreen();

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        System.out.println("-------------------------------------------------------");
        System.out.print("Enter Account Number: ");
        String account = in.nextLine();
        System.out.print("Enter PIN: ");
        String pin = in.nextLine();

        List<String> errors;
        errors = validation.login(account,pin);
        Boolean valid = true;
        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println("--> " + error);
            }
            valid = false;
        }
        // copy to global object
        AccountDao accountDao = AccountDaoImpl.getInstance();
        Account acct = accountDao.getLoginAccount(account, pin);
        Boolean isAccountFound = Objects.isNull(acct) ? false:true;
        if (isAccountFound){
            loginAccount.setBalance(acct.getBalance());
            loginAccount.setAccountNumber(acct.getAccountNumber());
            loginAccount.setName(acct.getName());
            loginAccount.setPin(acct.getPin());
        }

        if (valid && !isAccountFound){
            System.out.println("--> Invalid Account Number/PIN");
            resetLoginAccount();
            return;
        }

        if (!valid || !isAccountFound) return;

        transactionScreen.show();
    }
}
