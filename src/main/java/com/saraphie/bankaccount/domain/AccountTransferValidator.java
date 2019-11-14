package com.saraphie.bankaccount.domain;

import javax.validation.ValidationException;

public class AccountTransferValidator {

    public void validateAccountsForTransfer(Account sourceAccount, Account targetAccount) {
        if (sourceAccount.getBalance().getCurrency() != targetAccount.getBalance().getCurrency()) {
            throw new ValidationException("Currency of the source and target account does not match");
        }
    }
}
