package md.leonis.soap.dynamic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.*;

// Not working :(
@WebServiceProvider
@ServiceMode(Service.Mode.PAYLOAD)
public class HelloWorldWSProvider implements Provider<Source> {
    private static final String NAMESPACE = "http://localhost:8080/hello";

    public Source invoke(Source requestSource) {
        try {
            DOMResult requestResult = new DOMResult();
            getTransformer().transform(requestSource, requestResult);
            Node getHelloNode = ((Document) requestResult.getNode()).getDocumentElement();
            if (NAMESPACE.equals(getHelloNode.getNamespaceURI()) && "getHello".equals(getHelloNode.getLocalName())) {
                Node arg0Node = getHelloNode.getFirstChild();
                if ("arg0".equals(arg0Node.getNodeName())) {
                    String name = arg0Node.getTextContent();
                    String hello = String.format("Hello, %s!", name);
                    Document document = getDocumentBuilder().newDocument();
                    Element getHelloResponseElement = document.createElementNS(NAMESPACE, "getHelloResponse");
                    Element returnElement = document.createElement("return");
                    returnElement.appendChild(document.createTextNode(hello));
                    getHelloResponseElement.appendChild(returnElement);
                    document.appendChild(getHelloResponseElement);
                    return new DOMSource(document);
                }
            }
            throw new WebServiceException("Invalid request");
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Transformer getTransformer() {
        try {
            return TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static DocumentBuilder getDocumentBuilder() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }
}