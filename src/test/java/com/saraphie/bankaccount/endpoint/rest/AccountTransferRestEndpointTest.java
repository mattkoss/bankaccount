package com.saraphie.bankaccount.endpoint.rest;

import java.math.BigDecimal;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import com.saraphie.bankaccount.usecase.AccountTransferUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountTransferRestEndpointTest {

    @InjectMocks
    private AccountTransferRestEndpoint endpoint;

    @Mock
    private AccountTransferUseCase useCase;

    @Test
    void transfer() {
        TransferRequest request = new TransferRequest(new AccountId("40-40-40", "12345678"),
                new AccountId("50-50-50", "98765432"), new BigDecimal("23"));
        TransferResponse response = new TransferResponse(new AccountBalance(new BigDecimal("100"), "GBP"),
                new AccountBalance(new BigDecimal("479"), "GBP"));

        when(useCase.execute(any())).thenReturn(response);

        Response epResponse = endpoint.transfer(request);
        assertEquals(response, epResponse.getEntity());
    }
}
