package com.saraphie.bankaccount.endpoint.rest;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.saraphie.bankaccount.domain.AccountBalance;

@Path("/account-balance")
public class AccountBalanceRestEndpoint {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response balance(@QueryParam("sortCode") String sortCode,
            @QueryParam("accountNumber") String accountNumber) {
        AccountBalance accountBalance = new AccountBalance(new BigDecimal("23.44"), "GBP");
        return Response.status(200).entity(accountBalance).build();
    }

}
