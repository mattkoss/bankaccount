package com.saraphie.bankaccount.endpoint.rest;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountRestEndpoint {

    @GET
    @Path("/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response transfer(@PathParam("amount") BigDecimal amount) {
        return Response.status(200).entity("Transferring: " + amount).build();
    }

}
