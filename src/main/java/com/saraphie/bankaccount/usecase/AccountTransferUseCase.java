package com.saraphie.bankaccount.usecase;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.saraphie.bankaccount.domain.Account;
import com.saraphie.bankaccount.domain.repository.AccountRepository;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;

@Singleton
public class AccountTransferUseCase {

    @Inject
    private AccountRepository accountRepository;

    public TransferResponse execute(TransferRequest transferRequest) {

        // obtain locks for the source and target accounts
        // use module to maintain reasonable space complexity
        Account sourceAccount = accountRepository.getAccount(transferRequest.getSourceAccount());
        Account targetAccount = accountRepository.getAccount(transferRequest.getTargetAccount());

        // TODO: action transfer
        return new TransferResponse();
    }
}
