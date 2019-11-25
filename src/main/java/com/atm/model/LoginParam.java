package com.atm.model;

public class LoginParam {
    String accountNo;
    String pin;

    public LoginParam() {
    }

    public LoginParam(String accountNo, String pin) {
        this.accountNo = accountNo;
        this.pin = pin;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
