package com.saraphie.bankaccount.domain;

import java.util.concurrent.locks.ReadWriteLock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountLockingServiceTest {

    private AccountLockingService accountLockingService;

    @BeforeEach
    void setUp() {
        accountLockingService = new AccountLockingService();
    }

    @Test
    @DisplayName("when 2 threads are accessing the same account, second thread will lock")
    void getAccountLock_sameAccount() {

        AccountId accountId = new AccountId("40", "111");

        Runnable workflow1 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertTrue(lock.writeLock().tryLock());
        };

        new Thread(workflow1).start();

        sleep(100);

        Runnable workflow2 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertFalse(lock.writeLock().tryLock());
        };

        new Thread(workflow2).start();
    }

    @Test
    @DisplayName("when 2 threads are accessing different accounts, both threads will proceed")
    void getAccountLock_differentAccount() {

        AccountId accountId = new AccountId("41", "111");
        AccountId accountId2 = new AccountId("50", "112");

        Runnable workflow1 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertTrue(lock.writeLock().tryLock());
        };

        sleep(100);

        new Thread(workflow1).start();

        Runnable workflow2 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId2);
            assertTrue(lock.writeLock().tryLock());
        };

        new Thread(workflow2).start();
    }

    @Test
    @DisplayName("when 2 threads are accessing different accounts, with the same locking index, second thread will lock")
    void getAccountLock_differentAccountSameHash() {

        AccountId accountId = new AccountId("3357", "111");

        Runnable workflow1 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertTrue(lock.writeLock().tryLock());
        };

        new Thread(workflow1).start();

        sleep(100);

        AccountId accountId2 = new AccountId("7416", "111");

        Runnable workflow2 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId2);
            assertFalse(lock.writeLock().tryLock());
        };

        new Thread(workflow2).start();
    }

    @Test
    @DisplayName("when 2 threads are accessing the same account, but one only for reading, both will proceed")
    void getAccountLock_sameAccount_differentAccess() {

        AccountId accountId = new AccountId("60", "1124");

        Runnable workflow1 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertTrue(lock.readLock().tryLock());
        };

        new Thread(workflow1).start();

        Runnable workflow2 = () -> {
            ReadWriteLock lock = accountLockingService.getAccountLock(accountId);
            assertTrue(lock.readLock().tryLock());
        };

        new Thread(workflow2).start();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
