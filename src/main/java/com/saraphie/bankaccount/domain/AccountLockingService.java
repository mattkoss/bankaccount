package com.saraphie.bankaccount.domain;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.inject.Singleton;

import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;

@Singleton
public class AccountLockingService {

    private static final int MAX_LOCKS = 10000;

    // maintain finite number of locks
    // multiple accounts will then have the same lock, but this is better than using "synchronized"
    private ReadWriteLock[] accountLocks = new ReadWriteLock[MAX_LOCKS];

    public AccountLockingService() {
        // pre-initialise all locks
        for (int i = 0; i < MAX_LOCKS; i++) {
            accountLocks[i] = new ReentrantReadWriteLock();
        }
    }

    public ReadWriteLock getAccountLock(AccountId accountId) {
        int lockIndex = accountId.hashCode() % MAX_LOCKS;
        return accountLocks[lockIndex];
    }

}
