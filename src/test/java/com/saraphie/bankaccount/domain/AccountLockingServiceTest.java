package com.saraphie.bankaccount.domain;

import java.util.concurrent.locks.ReadWriteLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountLockingServiceTest {

    private AccountLockingService accountLockingService;

    @BeforeEach
    void setUp() {
        accountLockingService = new AccountLockingService();
    }

    @Test
    void getAccountLock() {
        AccountId accountId = new AccountId("40", "111");
        ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
    }

}
