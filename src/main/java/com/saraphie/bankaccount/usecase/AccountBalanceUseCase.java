package com.saraphie.bankaccount.usecase;

import java.util.concurrent.locks.ReadWriteLock;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.domain.repository.AccountRepository;

@Singleton
public class AccountBalanceUseCase {

    @Inject
    private AccountLockingService accountLockingService;

    @Inject
    private AccountRepository accountRepository;

    public AccountBalance execute(AccountId accountId) {
        // obtain lock
        ReadWriteLock lock = accountLockingService.getAccountLock(accountId);

        lock.readLock().lock();
        try {
            Account account = accountRepository.getAccount(accountId);

            return account.getBalance();
        } finally {
            lock.readLock().unlock();
        }

    }
}
