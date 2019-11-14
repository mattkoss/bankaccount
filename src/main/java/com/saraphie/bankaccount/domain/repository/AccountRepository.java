package com.saraphie.bankaccount.domain.repository;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountId;

public interface AccountRepository {

    Account getAccount(AccountId accountId);

}
