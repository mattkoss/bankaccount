package com.saraphie.bankaccount.usecase;

import java.math.BigDecimal;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.saraphie.bankaccount.dependency.BankAccountBinder;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTransferUseCaseIntegrationTest {

    @Inject
    private AccountTransferUseCase accountTransferUseCase;

    @BeforeEach
    void setUp() {
        ServiceLocator locator = ServiceLocatorUtilities.bind(new BankAccountBinder());
        locator.inject(this);
    }

    @Test
    void execute() {
        TransferRequest request = new TransferRequest(new AccountId("40-40-40", "12345678"),
                new AccountId("50-50-50", "98765432"), new BigDecimal("23"));

        TransferResponse response = accountTransferUseCase.execute(request);
        assertEquals(new TransferResponse(new AccountBalance(new BigDecimal("100"), "GBP"),
                new AccountBalance(new BigDecimal("479"), "GBP")), response);

        request = new TransferRequest(new AccountId("40-40-40", "12345678"), new AccountId("50-50-50", "98765432"),
                new BigDecimal("10"));

        response = accountTransferUseCase.execute(request);
        assertEquals(new TransferResponse(new AccountBalance(new BigDecimal("90"), "GBP"),
                new AccountBalance(new BigDecimal("489"), "GBP")), response);
    }
}
