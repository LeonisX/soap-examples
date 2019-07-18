package md.leonis.soap;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Arrays;

@WebService(endpointInterface = "md.leonis.soap.HelloWorldInterface")
public class HelloWorldWS implements HelloWorldInterface {

	@Override
	public String helloWorldWebMethod(String name) {
		return "Hello World JAX-WS " + name;
	}

	@Override
	public ArrayList<String> helloWorldArrayListWebMethod(String name) {
		return new ArrayList<>(Arrays.asList("Response", helloWorldWebMethod(name)));
	}
}
