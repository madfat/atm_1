package com.atm.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String accountId;
    private String name;
    @Size(min = 6, max = 6, message = "Must be 6 characters")
    private String accountNo;
    @Size(min = 6, max = 6, message = "Must be 6 characters")
    private String pin;
    private double balance;
    private Boolean status;

    public Account(String name, @Size(min = 6, max = 6, message = "Must be 6 characters") String accountNo, @Size(min = 6, max = 6, message = "Must be 6 characters") String pin, double balance, Boolean status) {
        this.name = name;
        this.accountNo = accountNo;
        this.pin = pin;
        this.balance = balance;
        this.status = status;
    }

    public Account() {
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", pin='" + pin + '\'' +
                ", balance=" + balance +
                ", accountNo='" + accountNo + '\'' +
                '}';
    }
}