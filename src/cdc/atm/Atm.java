package src.cdc.atm;

import src.cdc.atm.model.Account;
import src.cdc.atm.view.WelcomeScreen;

public class Atm {
    public static void main(String[] args) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        do {
            welcomeScreen.show();
        } while (true);
    }
}
