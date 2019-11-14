package com.saraphie.bankaccount.dependency;

import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.endpoint.rest.AccountBalanceRestEndpoint;
import com.saraphie.bankaccount.endpoint.rest.AccountTransferRestEndpoint;
import com.saraphie.bankaccount.usecase.AccountTransferUseCase;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class BankAccountConfig extends ResourceConfig {

    public BankAccountConfig() {

        // register all classes where resources need to be injected
        register(AccountBalanceRestEndpoint.class);
        register(AccountTransferRestEndpoint.class);

        // register binder with beans
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindAsContract(AccountTransferUseCase.class);
                bindAsContract(AccountLockingService.class);
            }
        });
    }

}
