package com.saraphie.bankaccount.endpoint.rest;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.usecase.AccountBalanceUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountBalanceRestEndpointTest {

    @InjectMocks
    private AccountBalanceRestEndpoint endpoint;

    @Mock
    private AccountBalanceUseCase useCase;

    @Test
    void balance() {
        AccountBalance balance = new AccountBalance(new BigDecimal("50"), "GBP");

        when(useCase.execute(any())).thenReturn(balance);

        Response response = endpoint.balance("40", "112");
        assertEquals(balance, response.getEntity());
    }
}
