
package md.leonis.soap.cxf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the md.leonis.soap.cxf package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _HelloWorldArrayListWebMethod_QNAME = new QName("http://soap.leonis.md/", "helloWorldArrayListWebMethod");
    private final static QName _HelloWorldArrayListWebMethodResponse_QNAME = new QName("http://soap.leonis.md/", "helloWorldArrayListWebMethodResponse");
    private final static QName _HelloWorldWebMethod_QNAME = new QName("http://soap.leonis.md/", "helloWorldWebMethod");
    private final static QName _HelloWorldWebMethodResponse_QNAME = new QName("http://soap.leonis.md/", "helloWorldWebMethodResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: md.leonis.soap.cxf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HelloWorldArrayListWebMethod }
     * 
     */
    public HelloWorldArrayListWebMethod createHelloWorldArrayListWebMethod() {
        return new HelloWorldArrayListWebMethod();
    }

    /**
     * Create an instance of {@link HelloWorldArrayListWebMethodResponse }
     * 
     */
    public HelloWorldArrayListWebMethodResponse createHelloWorldArrayListWebMethodResponse() {
        return new HelloWorldArrayListWebMethodResponse();
    }

    /**
     * Create an instance of {@link HelloWorldWebMethod }
     * 
     */
    public HelloWorldWebMethod createHelloWorldWebMethod() {
        return new HelloWorldWebMethod();
    }

    /**
     * Create an instance of {@link HelloWorldWebMethodResponse }
     * 
     */
    public HelloWorldWebMethodResponse createHelloWorldWebMethodResponse() {
        return new HelloWorldWebMethodResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorldArrayListWebMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloWorldArrayListWebMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.leonis.md/", name = "helloWorldArrayListWebMethod")
    public JAXBElement<HelloWorldArrayListWebMethod> createHelloWorldArrayListWebMethod(HelloWorldArrayListWebMethod value) {
        return new JAXBElement<HelloWorldArrayListWebMethod>(_HelloWorldArrayListWebMethod_QNAME, HelloWorldArrayListWebMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorldArrayListWebMethodResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloWorldArrayListWebMethodResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.leonis.md/", name = "helloWorldArrayListWebMethodResponse")
    public JAXBElement<HelloWorldArrayListWebMethodResponse> createHelloWorldArrayListWebMethodResponse(HelloWorldArrayListWebMethodResponse value) {
        return new JAXBElement<HelloWorldArrayListWebMethodResponse>(_HelloWorldArrayListWebMethodResponse_QNAME, HelloWorldArrayListWebMethodResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorldWebMethod }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloWorldWebMethod }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.leonis.md/", name = "helloWorldWebMethod")
    public JAXBElement<HelloWorldWebMethod> createHelloWorldWebMethod(HelloWorldWebMethod value) {
        return new JAXBElement<HelloWorldWebMethod>(_HelloWorldWebMethod_QNAME, HelloWorldWebMethod.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HelloWorldWebMethodResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link HelloWorldWebMethodResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://soap.leonis.md/", name = "helloWorldWebMethodResponse")
    public JAXBElement<HelloWorldWebMethodResponse> createHelloWorldWebMethodResponse(HelloWorldWebMethodResponse value) {
        return new JAXBElement<HelloWorldWebMethodResponse>(_HelloWorldWebMethodResponse_QNAME, HelloWorldWebMethodResponse.class, null, value);
    }

}
