package md.leonis.soap;

import javax.xml.ws.Endpoint;

public class HelloWorldWSPublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/hello", new HelloWorldWS());
        Endpoint.publish("http://localhost:8080/for/dispatcher", new HelloWorldWS());
    }

}