package md.leonis.soap;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@WebService(endpointInterface = "md.leonis.soap.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Resource
    WebServiceContext webServiceContext;

    @Override
    public String getHelloWorldAsStringPassword() {

        MessageContext messageContext = webServiceContext.getMessageContext();

        Map http_headers = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        List userList = (List) http_headers.get("username");
        List passList = (List) http_headers.get("password");

        String username = "";
        String password = "";

        if (userList != null) {
            username = userList.get(0).toString();
        }

        if (passList != null) {
            password = passList.get(0).toString();
        }

        if (username.equals("user") && password.equals("password")) {
            return "Hello World JAX-WS - Valid User!";
        } else {
            return "Unknown User!";
        }
    }

    @Override
    public String getHelloWorldAsStringBasic() {

        MessageContext messageContext = webServiceContext.getMessageContext();

        Map http_headers = (Map) messageContext.get(MessageContext.HTTP_REQUEST_HEADERS);

        List authList = (List) http_headers.get("Authorization");

        String username = "";
        String password = "";

        if (authList != null && authList.get(0).toString().toLowerCase().startsWith("basic")) {
            String basicAuthPayload = authList.get(0).toString();
            String payload = basicAuthPayload.replace("Basic ", "");
            byte[] decodedBytes = Base64.getDecoder().decode(payload);
            String usernameColonPassword = new String(decodedBytes);
            String[] values = usernameColonPassword.split(":");
            username = values[0];
            password = values[1];
        }

        if (username.equals("user") && password.equals("password")) {
            return "Hello World JAX-WS - Valid User!";
        } else {
            return "Unknown User!";
        }
    }
}
