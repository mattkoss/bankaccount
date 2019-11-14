package com.saraphie.bankaccount.domain.repository;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.endpoint.rest.dto.AccountId;

public interface AccountRepository {

    Account getAccount(AccountId sourceAccount);

}
