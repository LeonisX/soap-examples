package md.leonis.soap.client;

import md.leonis.soap.HelloWorldInterface;

import javax.xml.ws.Service;

public class HelloWorldWSClient {
 
	public static void main(String[] args) {

        Service service = new ServiceName();
 
        HelloWorldInterface hello = service.getPort(HelloWorldInterface.class);
 
        System.out.println(hello.helloWorldWebMethod("Leonis"));
    }
}
