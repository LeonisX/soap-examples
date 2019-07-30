package md.leonis.rs;

import md.leonis.ws.client.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;


@Path("/client")
public class Client {

    @Inject
    private ClientService clientService;

    @GET
    @Produces({"application/json"})
    public User getResponseFromService(@QueryParam("name") String name) {
        return clientService.callService(name);
    }
}
