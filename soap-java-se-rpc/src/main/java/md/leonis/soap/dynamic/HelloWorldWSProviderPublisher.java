package md.leonis.soap.dynamic;

import javax.xml.ws.Endpoint;

public class HelloWorldWSProviderPublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/hello/provider", new HelloWorldWSProvider());
    }

}