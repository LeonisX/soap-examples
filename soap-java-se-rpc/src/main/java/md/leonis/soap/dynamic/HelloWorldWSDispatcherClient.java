package md.leonis.soap.dynamic;

import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.net.URL;

public class HelloWorldWSDispatcherClient {

    public static void main(String[] args) throws Exception {

        URL url = new URL("http://localhost:8080/hello/provider?wsdl");

        QName qname = new QName("http://soap.leonis.md/", "HelloWorldWSService");

        Service service = Service.create(url, qname);

        Dispatch<Source> helloDispatch = service.createDispatch(
                        new QName("http://soap.leonis.md/", "HelloWorldWSPort"),
                        Source.class, Service.Mode.PAYLOAD);
        String request = "<ns2:helloWorldWebMethod xmlns:ns2=\"http://soap.leonis.md/\">\n" +
                "\t\t\t\t<arg0>Leonis</arg0>\n" + "\t\t\t</ns2:helloWorldWebMethod>";
        Source requestSource = new StreamSource(new StringReader(request));
        Source responseSource = helloDispatch.invoke(requestSource);
        Result responseResult = new StreamResult(System.out);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(responseSource, responseResult);

        /*Service helloService = Service.create(new QName("http://samples/hello", "HelloService"));
        helloService.addPort(
                new QName("http://samples/hello", "HelloPort"),
                SOAPBinding.SOAP11HTTP_BINDING,
                "http://localhost/hello"
        );*/
    }
}
