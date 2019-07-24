package demo.hw.server;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "User")
public class UserImpl implements User {

    private String name;

    // To avoid Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error: null
    public UserImpl() {
    }

    public UserImpl(String s) {
        name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }
}
