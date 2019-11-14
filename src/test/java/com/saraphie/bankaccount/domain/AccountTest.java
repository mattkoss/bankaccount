package com.saraphie.bankaccount.domain;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(new AccountId("33", "11"), "GBP", new BigDecimal(100));
    }

    @Test
    void getBalance() {
        AccountBalance balance = account.getBalance();

        assertEquals("GBP", balance.getCurrency());
        assertEquals(new BigDecimal(100), balance.getAmount());
    }

    @Test
    void getId() {
        assertEquals(new AccountId("33", "11"), account.getId());
    }

    @Nested
    @DisplayName("when depositing ")
    class Deposits {

        @Test
        @DisplayName("and positive amount is supplied, then amount is correctly deposited")
        void deposit_positive() {
            AccountBalance newBalance = account.deposit(new BigDecimal("123.4"));

            assertEquals(new AccountBalance(new BigDecimal("223.4"), "GBP"), newBalance);
        }

        @Test
        @DisplayName("and multiple deposits are supplied, then amounts are correctly deposited")
        void deposit_multiple() {
            account.deposit(new BigDecimal("20"));
            AccountBalance newBalance = account.deposit(new BigDecimal("10"));

            assertEquals(new AccountBalance(new BigDecimal("130"), "GBP"), newBalance);
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
        @DisplayName("and multiple withdrawals are done, then amounts are correctly withdrawn")
        void withdrawal_multiple() {
            account.withdraw(new BigDecimal("10"));
            AccountBalance newBalance = account.withdraw(new BigDecimal("50"));

            assertEquals(new AccountBalance(new BigDecimal("40"), "GBP"), newBalance);
        }

        @Test
        @DisplayName("and amount too large is supplied, exception is thrown")
        void withdrawal_tooLarge() {
            assertThrows(IllegalArgumentException.class, () -> account.withdraw(new BigDecimal("101")));
        }

        @Test
        @DisplayName("and amount that equals balance is supplied, then balance goes down to zero")
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
