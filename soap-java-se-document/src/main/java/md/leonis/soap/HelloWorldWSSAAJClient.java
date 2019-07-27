package md.leonis.soap;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HelloWorldWSSAAJClient {

    public static void main(String[] args) throws Exception {
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection connection = connectionFactory.createConnection();

        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage message = messageFactory.createMessage();

        message.getSOAPHeader().detachNode(); // We don't heed header here.

        SOAPBody body = message.getSOAPBody();
        QName bodyName = new QName("http://soap.leonis.md/", "helloWorldWebMethod", "ns2");
        SOAPBodyElement bodyElement = body.addBodyElement(bodyName);

        QName qName = new QName("arg0");
        SOAPElement argument = bodyElement.addChildElement(qName);
        argument.addTextNode("Leonis");

        System.out.println("Soap Request:");
        message.writeTo(System.out);
        System.out.println();

        URL endpoint = new URL("http://localhost:8080/hello");
        SOAPMessage response = connection.call(message, endpoint);

        System.out.println("\nSoap response:");
        System.out.println(envelopeToString(response));

        SOAPBody responseBody = response.getSOAPBody();
        System.out.println("\nSoap response body:");
        System.out.println(bodyToString(responseBody));

        System.out.println("Response:");
        System.out.println(responseBody.getElementsByTagName("return").item(0).getTextContent());
    }

    private static String envelopeToString(SOAPMessage response) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        response.writeTo(stream);
        return new String(stream.toByteArray(), StandardCharsets.UTF_8);
    }

    private static String bodyToString(SOAPBody message) throws Exception {
        DOMSource source = new DOMSource(message);
        StringWriter stringResult = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(source, new StreamResult(stringResult));
        return stringResult.toString();
    }
}
