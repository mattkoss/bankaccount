package com.saraphie.bankaccount.dependency;

import javax.inject.Singleton;

import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.domain.AccountTransferValidator;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import com.saraphie.bankaccount.domain.repository.InMemoryAccountRepository;
import com.saraphie.bankaccount.usecase.AccountBalanceUseCase;
import com.saraphie.bankaccount.usecase.AccountTransferUseCase;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class BankAccountBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindAsContract(AccountTransferUseCase.class);
        bindAsContract(AccountBalanceUseCase.class);

        bindAsContract(AccountLockingService.class).in(Singleton.class);
        bindAsContract(AccountTransferValidator.class).in(Singleton.class);

        bind(InMemoryAccountRepository.class).to(AccountRepository.class).in(Singleton.class);
    }

}

