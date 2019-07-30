package md.leonis.rs;

import md.leonis.ws.client.Dates;
import md.leonis.ws.client.User;
import md.leonis.ws.client.UserService;
import md.leonis.ws.client.UserService_Service;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;

public class ClientService {

    private UserService userService;

    public ClientService() {
        /*UserService_Service ss = new UserService_Service();
        userService = ss.getUserServiceImplPort();*/

        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(UserService.class);
        factory.setAddress("http://localhost:8080/ws/userService");
        userService = (UserService) factory.create();

        Client client = ClientProxy.getClient(userService);
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());

        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        conduit.getAuthorization().setUserName("user");
        conduit.getAuthorization().setPassword("password");
    }

    User callService(String name) {
        User user = userService.create();
        user.setName(name);
        user = userService.save(user);
        userService.updateDates(user.getId(), new Dates());
        userService.addHobbie(user.getId(), "New hobbie");
        userService.addNote(user.getId(), "Biology", 10);

        return userService.get(user.getId());
    }
}
