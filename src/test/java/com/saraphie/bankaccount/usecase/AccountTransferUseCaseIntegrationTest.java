package com.saraphie.bankaccount.usecase;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.saraphie.bankaccount.dependency.BankAccountBinder;
import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTransferUseCaseIntegrationTest {

    @Inject
    private AccountTransferUseCase accountTransferUseCase;

    @Inject
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        ServiceLocator locator = ServiceLocatorUtilities.bind(new BankAccountBinder());
        locator.inject(this);
    }

    @Test
    @DisplayName("when 2 separate sub-sequent requests are executed, balances are correctly updated")
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

    @Test
    @DisplayName("when 2 separate concurrent requests are executed, balances are correctly updated")
    void execute_concurrent() {
        final AccountId accountId = new AccountId("40-40-40", "12345678");
        final AccountId accountId2 = new AccountId("50-50-50", "98765432");

        Account account = accountRepository.getAccount(accountId);
        AccountBalance balance1 = account.getBalance();
        Account account2 = accountRepository.getAccount(accountId2);
        AccountBalance balance2 = account2.getBalance();

        Runnable workflow1 = () -> {
            TransferRequest request = new TransferRequest(accountId, accountId2, new BigDecimal("23"));

            accountTransferUseCase.execute(request);
        };

        Runnable workflow2 = () -> {
            TransferRequest request2 = new TransferRequest(accountId, accountId2, new BigDecimal("10"));

            accountTransferUseCase.execute(request2);
        };

        new Thread(workflow1).start();
        new Thread(workflow2).start();

        sleep(100);

        account = accountRepository.getAccount(accountId);
        account2 = accountRepository.getAccount(accountId2);
        assertEquals(new AccountBalance(new BigDecimal(balance1.getAmount().intValue() - 33), "GBP"),
                account.getBalance());
        assertEquals(new AccountBalance(new BigDecimal(balance2.getAmount().intValue() + 33), "GBP"),
                account2.getBalance());
    }

    @Test
    @DisplayName("when 2 separate concurrent mirrored requests are executed, balances are correctly updated and deadlock does not happen")
    void execute_concurrent_mirrored() {
        final AccountId accountId = new AccountId("40-40-40", "12345678");
        final AccountId accountId2 = new AccountId("50-50-50", "98765432");

        Account account = accountRepository.getAccount(accountId);
        AccountBalance balance1 = account.getBalance();
        Account account2 = accountRepository.getAccount(accountId2);
        AccountBalance balance2 = account2.getBalance();

        final int THREAD_COUNT = 100;
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        // one workflow transfers from account1 to account2
        Runnable workflow1 = () -> {
            TransferRequest request = new TransferRequest(accountId, accountId2, new BigDecimal("1"));

            accountTransferUseCase.execute(request);
            latch.countDown();
        };

        // other workflow transfers the other way around
        Runnable workflow2 = () -> {
            TransferRequest request2 = new TransferRequest(accountId2, accountId, new BigDecimal("2"));

            accountTransferUseCase.execute(request2);
            latch.countDown();
        };

        // create half threads with one workflow and the other half with the other workflow
        boolean wfOne = true;
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(wfOne
                       ? workflow1
                       : workflow2).start();
            wfOne = !wfOne;
        }

        try {
            boolean result = latch.await(1000, TimeUnit.MILLISECONDS);
            // none of the threads should time out, i.e. there should not be a deadlock
            assertTrue(result, "One of the threads deadlocked");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        account = accountRepository.getAccount(accountId);
        account2 = accountRepository.getAccount(accountId2);
        assertEquals(new AccountBalance(new BigDecimal(balance1.getAmount().intValue() + (THREAD_COUNT / 2)), "GBP"),
                account.getBalance());
        assertEquals(new AccountBalance(new BigDecimal(balance2.getAmount().intValue() - (THREAD_COUNT / 2)), "GBP"),
                account2.getBalance());
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
