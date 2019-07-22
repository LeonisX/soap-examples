package md.leonis.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloWorld {

    @WebMethod
    String getHelloWorldAsStringPassword();

    @WebMethod
    String getHelloWorldAsStringBasic();
}
