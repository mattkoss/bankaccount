package com.saraphie.bankaccount.usecase;

import java.util.concurrent.locks.ReadWriteLock;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountLockingService;
import com.saraphie.bankaccount.domain.AccountTransferValidator;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AccountTransferUseCase {

    private final static Logger LOG = LoggerFactory.getLogger(AccountTransferUseCase.class);

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private AccountLockingService accountLockingService;

    @Inject
    private AccountTransferValidator accountTransferValidator;

    public TransferResponse execute(TransferRequest transferRequest) {
        LOG.info("Executing transfer: {}", transferRequest);

        // obtain locks for the source and target accounts
        ReadWriteLock lock = accountLockingService.getAccountLock(transferRequest.getSourceAccount());
        ReadWriteLock lock2 = accountLockingService.getAccountLock(transferRequest.getTargetAccount());

        lock.writeLock().lock();
        lock2.writeLock().lock();
        try {
            Account sourceAccount = accountRepository.getAccount(transferRequest.getSourceAccount());
            Account targetAccount = accountRepository.getAccount(transferRequest.getTargetAccount());

            accountTransferValidator.validateAccountsForTransfer(sourceAccount, targetAccount);

            AccountBalance sourceAccountBalance = sourceAccount.withdraw(transferRequest.getAmount());
            AccountBalance targetAccountBalance = targetAccount.deposit(transferRequest.getAmount());

            LOG.info("Transfer successful: {}, {}", sourceAccountBalance, targetAccountBalance);

            return new TransferResponse(sourceAccountBalance, targetAccountBalance);

        } finally {
            lock2.writeLock().unlock();
            lock.writeLock().unlock();
        }
    }

}
