package com.saraphie.bankaccount.domain;

import java.math.BigDecimal;

import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;

public class Account {

    private final AccountId id;
    private final String currency;
    private BigDecimal balance;

    public Account(AccountId id, String currency, BigDecimal balance) {
        this.id = id;
        this.currency = currency;
        this.balance = balance;
    }

    public AccountId getId() {
        return id;
    }

    public AccountBalance getBalance() {
        return new AccountBalance(balance, currency);
    }

    public AccountBalance deposit(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Only positive amounts can be deposited");
        }
        BigDecimal newBalance = this.balance.add(amount);
        this.balance = newBalance;

        return this.getBalance();
    }

    public AccountBalance withdraw(BigDecimal amount) {

        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("Only positive amounts can be withdrawn");
        } else if (amount.compareTo(this.balance) > 0) {
            throw new IllegalArgumentException("Amount too large for a withdrawal");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        this.balance = newBalance;

        return this.getBalance();
    }

}
