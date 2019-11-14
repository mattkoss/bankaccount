package com.saraphie.bankaccount.endpoint.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.saraphie.bankaccount.endpoint.rest.dto.TransferRequest;
import com.saraphie.bankaccount.endpoint.rest.dto.TransferResponse;
import com.saraphie.bankaccount.usecase.AccountTransferUseCase;

@Path("/transfer")
public class AccountTransferRestEndpoint {

    @Inject
    private AccountTransferUseCase useCase;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response transfer(TransferRequest transferRequest) {
        TransferResponse response = useCase.execute(transferRequest);
        return Response.status(200).entity(response).build();
    }
}
