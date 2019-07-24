package demo.hw.server;

import java.util.Collections;

import org.apache.cxf.ext.logging.LoggingFeature;
//import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ServerFactoryBean;

public class Server {

    protected Server() {
        HelloWorldImpl helloworldImpl = new HelloWorldImpl();
        ServerFactoryBean svrFactory = new ServerFactoryBean();
        svrFactory.setServiceClass(HelloWorld.class);
        svrFactory.setAddress("http://localhost:9000/Hello");
        svrFactory.setServiceBean(helloworldImpl);
        svrFactory.setFeatures(Collections.singletonList(new LoggingFeature()));
        //svrFactory.getServiceFactory().setDataBinding(new AegisDatabinding());
        svrFactory.create();
    }

    public static void main(String[] args) throws Exception {
        new Server();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}
