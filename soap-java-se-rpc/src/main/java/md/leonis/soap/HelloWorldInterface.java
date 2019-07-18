package md.leonis.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.ArrayList;

@WebService
@SOAPBinding(style = Style.RPC)
public interface HelloWorldInterface {

	@WebMethod
	String helloWorldWebMethod(String name);

	@WebMethod
	ArrayList<String> helloWorldArrayListWebMethod(String name);
}
