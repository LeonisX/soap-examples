package md.leonis.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "HelloWorldInterface")
public interface HelloWorldInterface {

    @WebMethod
    String helloWorldWebMethod(String name);
}
