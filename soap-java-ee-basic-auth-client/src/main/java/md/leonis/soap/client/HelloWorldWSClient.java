package md.leonis.soap.client;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.util.*;

public class HelloWorldWSClient {

    public static void main(String[] args) {

        Service service;

        if (args.length > 0) {
            service = new ServiceName(args[0]);
        } else {
            service = new ServiceName();
        }

        HelloWorldInterface hello = service.getPort(HelloWorldInterface.class);

        Map<String, Object> req_ctx = ((BindingProvider) hello).getRequestContext();

        Map<String, List<String>> headers = new HashMap<>();

        String usernameColonPassword = "user:password";
        String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        headers.put("Authorization", Collections.singletonList(basicAuthPayload));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        System.out.println(hello.helloWorldWebMethod("Leonis"));
    }
}
