package demo.hw.client;

//import org.apache.cxf.aegis.databinding.AegisDatabinding;
import org.apache.cxf.frontend.ClientProxyFactoryBean;
import demo.hw.server.HelloWorld;

public final class Client {

    public static void main(String[] args) {
        ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
        if (args != null && args.length > 0 && !"".equals(args[0])) {
            factory.setAddress(args[0]);
        } else {
            factory.setAddress("http://localhost:9000/Hello");
        }
        //factory.getServiceFactory().setDataBinding(new AegisDatabinding());
        HelloWorld client = factory.create(HelloWorld.class);
        System.out.println("Invoke sayHi()....");
        System.out.println(client.sayHi(System.getProperty("user.name")));
        System.exit(0);
    }

}
