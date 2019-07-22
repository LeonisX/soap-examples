
package md.leonis.soap.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "HelloWorldInterface", targetNamespace = "http://soap.leonis.md/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface HelloWorldInterface {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "helloWorldWebMethod", targetNamespace = "http://soap.leonis.md/", className = "md.leonis.soap.client.HelloWorldWebMethod")
    @ResponseWrapper(localName = "helloWorldWebMethodResponse", targetNamespace = "http://soap.leonis.md/", className = "md.leonis.soap.client.HelloWorldWebMethodResponse")
    public String helloWorldWebMethod(
            @WebParam(name = "arg0", targetNamespace = "")
                    String arg0);

}