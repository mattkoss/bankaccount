package com.saraphie.bankaccount.domain;

import java.math.BigDecimal;
import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTransferValidatorTest {

    private AccountTransferValidator accountTransferValidator = new AccountTransferValidator();

    @DisplayName("when both accounts have the same currency, then succeed")
    @Test
    void validateAccountsForTransfer_sameCurrency() {
        Account sourceAccount = new Account(new AccountId("11", "22"), "GBP", new BigDecimal("12"));
        Account targetAccount = new Account(new AccountId("33", "44"), "GBP", new BigDecimal("12"));

        accountTransferValidator.validateAccountsForTransfer(sourceAccount, targetAccount);
    }

    @DisplayName("when accounts are in different currencies, throw exception")
    @Test
    void validateAccountsForTransfer_differentCurrency() {
        Account sourceAccount = new Account(new AccountId("11", "22"), "GBP", new BigDecimal("12"));
        Account targetAccount = new Account(new AccountId("33", "44"), "USD", new BigDecimal("12"));

        assertThrows(ValidationException.class,
                () -> accountTransferValidator.validateAccountsForTransfer(sourceAccount, targetAccount));
    }
}
