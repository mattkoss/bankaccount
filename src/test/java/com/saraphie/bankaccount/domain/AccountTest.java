package com.saraphie.bankaccount.domain;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AccountTest {

    Account account = new Account(new AccountId("33", "11"), "GBP", new BigDecimal(100));

    @Nested
    @DisplayName("when depositing ")
    class Deposits {

        @Test
        @DisplayName("and positive amount is supplied")
        void deposit_positive() {
            AccountBalance newBalance = account.deposit(new BigDecimal("123.4"));

            assertEquals(new AccountBalance(new BigDecimal("223.4"), "GBP"), newBalance);
        }

        @Test
        @DisplayName("and negative amount is supplied, exception is thrown")
        void deposit_negative() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal("-123.4")));
        }

        @Test
        @DisplayName("and zero amount is supplied, exception is thrown")
        void deposit_zero() {
            assertThrows(IllegalArgumentException.class, () -> account.deposit(new BigDecimal("0")));
        }
    }

    @Nested
    @DisplayName("when withdrawing")
    class Withdrawals {
        @Test
        @DisplayName("and positive amount is supplied, then amount is correctly withdrawn")
        void withdrawal_positive() {
            AccountBalance newBalance = account.withdraw(new BigDecimal("50"));

            assertEquals(new AccountBalance(new BigDecimal("50"), "GBP"), newBalance);
        }

        @Test
        @DisplayName("and amount too large is supplied, exception is thrown")
        void withdrawal_tooLarge() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(new BigDecimal("101")));
        }

        @Test
        @DisplayName("and amount that equals balance is suppied, then balance goes down to zero")
        void withdrawal_sameAmount() {
            AccountBalance newBalance = account.withdraw(new BigDecimal("100"));

            assertEquals(new AccountBalance(new BigDecimal("0"), "GBP"), newBalance);
        }

        @Test
        @DisplayName("and negative amount is supplied, exception is thrown")
        void withdrawal_negative() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(new BigDecimal("-123.4")));
        }

        @Test
        @DisplayName("and zero amount is supplied, exception is thrown")
        void withdrawal_zero() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(new BigDecimal("0")));
        }

    }
}
