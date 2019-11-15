package com.saraphie.bankaccount.usecase;

import java.util.concurrent.locks.ReadWriteLock;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AccountBalanceUseCase {

    private final static Logger LOG = LoggerFactory.getLogger(AccountBalanceUseCase.class);

    @Inject
    private AccountLockingService accountLockingService;

    @Inject
    private AccountRepository accountRepository;

    public AccountBalance execute(AccountId accountId) {
        LOG.info("Querying balance for {}", accountId);

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
