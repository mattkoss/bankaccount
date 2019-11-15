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
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountBalanceUseCaseTest {

    @InjectMocks
    private AccountBalanceUseCase useCase;

    @Mock
    private AccountRepository repository;
    @Mock
    private AccountLockingService lockingService;

    @Test
    void execute() {
        AccountId accountId = new AccountId("40-40-40", "12345678");

        when(lockingService.getAccountLock(any())).thenReturn(new ReentrantReadWriteLock());
        Account account1 = new Account(accountId, "GBP", new BigDecimal("50"));
        when(repository.getAccount(accountId)).thenReturn(account1);

        AccountBalance balance = useCase.execute(accountId);

        assertEquals(new AccountBalance(new BigDecimal("50"), "GBP"), balance);
    }
}
