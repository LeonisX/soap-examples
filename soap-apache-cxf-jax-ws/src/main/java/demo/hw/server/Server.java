package demo.hw.server;

import javax.xml.ws.Endpoint;

import org.apache.cxf.ext.logging.LoggingFeature;

public class Server {

    private Server() {
        System.out.println("Starting Server");
        HelloWorldImpl implementor = new HelloWorldImpl();
        String address = "http://localhost:9000/helloWorld";
        Endpoint.publish(address, implementor, new LoggingFeature());
    }

    public static void main(String[] args) throws Exception {
        new Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
