package md.leonis.soap;

import javax.jws.WebService;

@WebService(serviceName = "serviceName", portName = "portName",
        endpointInterface = "md.leonis.soap.HelloWorldInterface", targetNamespace = "http://soap.leonis.md/")
public class HelloWorldWS implements HelloWorldInterface {

    @Override
    public String helloWorldWebMethod(String name) {
        return "Hello World JAX-WS " + name;
    }
}
