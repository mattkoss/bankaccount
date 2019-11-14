package com.saraphie.bankaccount.endpoint.rest.dto;

import com.saraphie.bankaccount.domain.AccountBalance;

public class TransferResponse {

    private AccountBalance sourceAccountBalance;
    private AccountBalance targetAccountBalance;

    public TransferResponse(AccountBalance sourceAccountBalance, AccountBalance targetAccountBalance) {
        this.sourceAccountBalance = sourceAccountBalance;
        this.targetAccountBalance = targetAccountBalance;
    }

    public AccountBalance getSourceAccountBalance() {
        return sourceAccountBalance;
    }

    public AccountBalance getTargetAccountBalance() {
        return targetAccountBalance;
    }
}
