
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package md.leonis.soap.cxf;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.3.2
 * 2019-07-18T17:10:11.134+03:00
 * Generated source version: 3.3.2
 *
 */

@javax.jws.WebService(
                      serviceName = "HelloWorldWSService",
                      portName = "HelloWorldWSPort",
                      targetNamespace = "http://soap.leonis.md/",
                      wsdlLocation = "classpath:wsdl/HelloWorldWSService.wsdl",
                      endpointInterface = "md.leonis.soap.cxf.HelloWorldInterface")

public class HelloWorldWSPortImpl implements HelloWorldInterface {

    private static final Logger LOG = Logger.getLogger(HelloWorldWSPortImpl.class.getName());

    /* (non-Javadoc)
     * @see md.leonis.soap.cxf.HelloWorldInterface#helloWorldWebMethod(java.lang.String arg0)*
     */
    public java.lang.String helloWorldWebMethod(java.lang.String arg0) {
        LOG.info("Executing operation helloWorldWebMethod");
        System.out.println(arg0);
        try {
            java.lang.String _return = "";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see md.leonis.soap.cxf.HelloWorldInterface#helloWorldArrayListWebMethod(java.lang.String arg0)*
     */
    public java.util.List<java.lang.String> helloWorldArrayListWebMethod(java.lang.String arg0) {
        LOG.info("Executing operation helloWorldArrayListWebMethod");
        System.out.println(arg0);
        try {
            java.util.List<java.lang.String> _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
