package md.leonis.ws.server.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private int id;
    private String name;
    private BigDecimal uid;
    private Dates dates = new Dates();
    private Map<String, Integer> notes = new HashMap<>();
    private List<String> hobbies = new ArrayList<>();

    // To avoid Exception in thread "main" javax.xml.ws.soap.SOAPFaultException: Unmarshalling Error: null
    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.uid = new BigDecimal(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUid() {
        return uid;
    }

    public void setUid(BigDecimal uid) {
        this.uid = uid;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public Map<String, Integer> getNotes() {
        return notes;
    }

    public void setNotes(HashMap<String, Integer> notes) {
        this.notes = notes;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(ArrayList<String> hobbies) {
        this.hobbies = hobbies;
    }
}
