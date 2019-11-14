package com.saraphie.bankaccount.domain.repository;

import java.util.concurrent.locks.ReadWriteLock;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;

public class InMemoryAccountRepository extends LockingAccountRepository implements AccountRepository {

    @Override
    public Account getAccount(AccountId accountId) {
        ReadWriteLock lock = getAccountLock(accountId);
        Account account = getAccountInternal(accountId);
        return account;
    }

    private Account getAccountInternal(AccountId accountId) {
        // TODO: retrieve account;
        return null;
    }
}
