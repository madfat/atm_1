package com.atm.view;

import java.util.Scanner;

public class FundTransferGenerateRefNoScreen extends CommonScreen{
    Scanner in = new Scanner(System.in);
    public void show(String refNo) {
        System.out.println(String.format("\nReference Number: %s", refNo));
        System.out.print("Press enter to continue");
        in.nextLine();
    }
}
