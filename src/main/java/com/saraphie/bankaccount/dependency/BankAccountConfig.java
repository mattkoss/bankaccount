package com.saraphie.bankaccount.dependency;

import com.saraphie.bankaccount.endpoint.rest.AccountBalanceRestEndpoint;
import com.saraphie.bankaccount.endpoint.rest.AccountTransferRestEndpoint;
import org.glassfish.jersey.server.ResourceConfig;

public class BankAccountConfig extends ResourceConfig {

    public BankAccountConfig() {

        // register all classes where resources need to be injected
        register(AccountBalanceRestEndpoint.class);
        register(AccountTransferRestEndpoint.class);

        // register binder with beans
        register(new BankAccountBinder());
    }

}
