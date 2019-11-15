package com.saraphie.bankaccount.usecase;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.domain.AccountTransferValidator;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountTransferUseCaseTest {

    @InjectMocks
    private AccountTransferUseCase useCase;

    @Mock
    private AccountRepository repository;
    @Mock
    private AccountLockingService lockingService;
    @Mock
    private AccountTransferValidator validator;

    @Test
    void execute() {
        AccountId accountId1 = new AccountId("40-40-40", "12345678");
        AccountId accountId2 = new AccountId("50-50-50", "98765432");

        TransferRequest request = new TransferRequest(accountId1, accountId2, new BigDecimal("20"));

        when(lockingService.getAccountLock(any())).thenReturn(new ReentrantReadWriteLock());
        Account account1 = new Account(accountId1, "GBP", new BigDecimal("50"));
        Account account2 = new Account(accountId2, "GBP", new BigDecimal("40"));
        when(repository.getAccount(accountId1)).thenReturn(account1);
        when(repository.getAccount(accountId2)).thenReturn(account2);

        TransferResponse response = useCase.execute(request);
        assertEquals(new TransferResponse(new AccountBalance(new BigDecimal("30"), "GBP"),
                new AccountBalance(new BigDecimal("60"), "GBP")), response);
    }
}
