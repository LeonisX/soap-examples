package md.leonis.soap;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class HelloWorldWSClient {
 
	public static void main(String[] args) throws Exception {
 
		URL url = new URL("http://localhost:8080/hello?wsdl");
 
        //1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://soap.leonis.md/", "HelloWorldWSService");
 
        Service service = Service.create(url, qname);

        HelloWorldInterface hello = service.getPort(HelloWorldInterface.class);
 
        System.out.println(hello.helloWorldWebMethod("Leonis"));
        System.out.println(hello.helloWorldArrayListWebMethod("Leonis"));
    }
}
