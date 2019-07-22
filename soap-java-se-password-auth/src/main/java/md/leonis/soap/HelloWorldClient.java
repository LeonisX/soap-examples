package md.leonis.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.URL;
import java.util.*;

public class HelloWorldClient {

    private static final String WS_URL = "http://localhost:8080/hello?wsdl";

    public static void main(String[] args) throws Exception {

        URL url = new URL(WS_URL);
        QName qname = new QName("http://soap.leonis.md/", "HelloWorldImplService");

        Service service = Service.create(url, qname);
        HelloWorld hello = service.getPort(HelloWorld.class);

        Map<String, Object> req_ctx = ((BindingProvider) hello).getRequestContext();

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("username", Collections.singletonList("user"));
        headers.put("password", Collections.singletonList("password"));

        String usernameColonPassword = "user:password";
        String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        headers.put("Authorization", Collections.singletonList(basicAuthPayload));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        System.out.println(hello.getHelloWorldAsStringPassword());
        System.out.println(hello.getHelloWorldAsStringBasic());
    }
}
