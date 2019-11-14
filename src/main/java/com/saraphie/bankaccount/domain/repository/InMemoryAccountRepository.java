package com.saraphie.bankaccount.domain.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.inject.Singleton;
import javax.ws.rs.NotFoundException;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountId;

@Singleton
public class InMemoryAccountRepository implements AccountRepository {

    private HashMap<AccountId, Account> accountStore;

    public InMemoryAccountRepository() {
        this.accountStore = new LinkedHashMap<>();

        // store some accounts initially
        AccountId accountId = new AccountId("40-40-40", "12345678");
        Account account = new Account(accountId, "GBP", new BigDecimal("123"));
        accountStore.put(accountId, account);

        accountId = new AccountId("50-50-50", "98765432");
        account = new Account(accountId, "GBP", new BigDecimal("456"));
        accountStore.put(accountId, account);

        accountId = new AccountId("60-60-60", "11110022");
        account = new Account(accountId, "USD", new BigDecimal("1024"));
        accountStore.put(accountId, account);
    }

    @Override
    public Account getAccount(AccountId accountId) {
        Account account = accountStore.get(accountId);
        if (account == null) {
            throw new NotFoundException("Account " + accountId + " not found");
        }
        return account;
    }
}
