package controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/osoby")
public class OsobyController {

    @GET
    @Produces("application/json")
    public Response get() {
        try {
            return Response.status(303).location(new URI("/users")).build();
        } catch (URISyntaxException e) {
            return Response.status(500).build();
        }
    }
}
