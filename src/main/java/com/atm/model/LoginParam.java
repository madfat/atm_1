package com.atm.model;

public class LoginParam {
    String accountNo;
    String pin;
    private String username;
    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
