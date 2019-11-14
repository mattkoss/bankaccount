package com.saraphie.bankaccount.endpoint.rest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.saraphie.bankaccount.domain.AccountBalance;
import com.saraphie.bankaccount.domain.AccountId;
import com.saraphie.bankaccount.usecase.AccountBalanceUseCase;

@Path("/account-balance")
public class AccountBalanceRestEndpoint {

    @Inject
    private AccountBalanceUseCase accountBalanceUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response balance(@QueryParam("sortCode") String sortCode,
            @QueryParam("accountNumber") String accountNumber) {
        AccountBalance accountBalance = accountBalanceUseCase.execute(new AccountId(sortCode, accountNumber));
        return Response.status(200).entity(accountBalance).build();
    }

}
