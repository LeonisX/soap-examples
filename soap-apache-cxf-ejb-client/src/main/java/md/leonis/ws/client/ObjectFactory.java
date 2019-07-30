
package md.leonis.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the md.leonis.ws.client package. 
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

    private final static QName _AddHobbie_QNAME = new QName("http://server.ws.leonis.md/", "addHobbie");
    private final static QName _AddHobbieResponse_QNAME = new QName("http://server.ws.leonis.md/", "addHobbieResponse");
    private final static QName _AddNote_QNAME = new QName("http://server.ws.leonis.md/", "addNote");
    private final static QName _AddNoteResponse_QNAME = new QName("http://server.ws.leonis.md/", "addNoteResponse");
    private final static QName _Create_QNAME = new QName("http://server.ws.leonis.md/", "create");
    private final static QName _CreateResponse_QNAME = new QName("http://server.ws.leonis.md/", "createResponse");
    private final static QName _Get_QNAME = new QName("http://server.ws.leonis.md/", "get");
    private final static QName _GetResponse_QNAME = new QName("http://server.ws.leonis.md/", "getResponse");
    private final static QName _Save_QNAME = new QName("http://server.ws.leonis.md/", "save");
    private final static QName _SaveResponse_QNAME = new QName("http://server.ws.leonis.md/", "saveResponse");
    private final static QName _UpdateDates_QNAME = new QName("http://server.ws.leonis.md/", "updateDates");
    private final static QName _UpdateDatesResponse_QNAME = new QName("http://server.ws.leonis.md/", "updateDatesResponse");
    private final static QName _User_QNAME = new QName("http://server.ws.leonis.md/", "user");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: md.leonis.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link User.Notes }
     * 
     */
    public User.Notes createUserNotes() {
        return new User.Notes();
    }

    /**
     * Create an instance of {@link AddHobbie }
     * 
     */
    public AddHobbie createAddHobbie() {
        return new AddHobbie();
    }

    /**
     * Create an instance of {@link AddHobbieResponse }
     * 
     */
    public AddHobbieResponse createAddHobbieResponse() {
        return new AddHobbieResponse();
    }

    /**
     * Create an instance of {@link AddNote }
     * 
     */
    public AddNote createAddNote() {
        return new AddNote();
    }

    /**
     * Create an instance of {@link AddNoteResponse }
     * 
     */
    public AddNoteResponse createAddNoteResponse() {
        return new AddNoteResponse();
    }

    /**
     * Create an instance of {@link Create }
     * 
     */
    public Create createCreate() {
        return new Create();
    }

    /**
     * Create an instance of {@link CreateResponse }
     * 
     */
    public CreateResponse createCreateResponse() {
        return new CreateResponse();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link Save }
     * 
     */
    public Save createSave() {
        return new Save();
    }

    /**
     * Create an instance of {@link SaveResponse }
     * 
     */
    public SaveResponse createSaveResponse() {
        return new SaveResponse();
    }

    /**
     * Create an instance of {@link UpdateDates }
     * 
     */
    public UpdateDates createUpdateDates() {
        return new UpdateDates();
    }

    /**
     * Create an instance of {@link UpdateDatesResponse }
     * 
     */
    public UpdateDatesResponse createUpdateDatesResponse() {
        return new UpdateDatesResponse();
    }

    /**
     * Create an instance of {@link Dates }
     * 
     */
    public Dates createDates() {
        return new Dates();
    }

    /**
     * Create an instance of {@link User.Notes.Entry }
     * 
     */
    public User.Notes.Entry createUserNotesEntry() {
        return new User.Notes.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHobbie }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddHobbie }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "addHobbie")
    public JAXBElement<AddHobbie> createAddHobbie(AddHobbie value) {
        return new JAXBElement<AddHobbie>(_AddHobbie_QNAME, AddHobbie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddHobbieResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddHobbieResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "addHobbieResponse")
    public JAXBElement<AddHobbieResponse> createAddHobbieResponse(AddHobbieResponse value) {
        return new JAXBElement<AddHobbieResponse>(_AddHobbieResponse_QNAME, AddHobbieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNote }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddNote }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "addNote")
    public JAXBElement<AddNote> createAddNote(AddNote value) {
        return new JAXBElement<AddNote>(_AddNote_QNAME, AddNote.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNoteResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddNoteResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "addNoteResponse")
    public JAXBElement<AddNoteResponse> createAddNoteResponse(AddNoteResponse value) {
        return new JAXBElement<AddNoteResponse>(_AddNoteResponse_QNAME, AddNoteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Create }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Create }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "create")
    public JAXBElement<Create> createCreate(Create value) {
        return new JAXBElement<Create>(_Create_QNAME, Create.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "createResponse")
    public JAXBElement<CreateResponse> createCreateResponse(CreateResponse value) {
        return new JAXBElement<CreateResponse>(_CreateResponse_QNAME, CreateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Get }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Get }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "get")
    public JAXBElement<Get> createGet(Get value) {
        return new JAXBElement<Get>(_Get_QNAME, Get.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "getResponse")
    public JAXBElement<GetResponse> createGetResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetResponse_QNAME, GetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Save }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Save }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "save")
    public JAXBElement<Save> createSave(Save value) {
        return new JAXBElement<Save>(_Save_QNAME, Save.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SaveResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "saveResponse")
    public JAXBElement<SaveResponse> createSaveResponse(SaveResponse value) {
        return new JAXBElement<SaveResponse>(_SaveResponse_QNAME, SaveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDates }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateDates }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "updateDates")
    public JAXBElement<UpdateDates> createUpdateDates(UpdateDates value) {
        return new JAXBElement<UpdateDates>(_UpdateDates_QNAME, UpdateDates.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDatesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UpdateDatesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "updateDatesResponse")
    public JAXBElement<UpdateDatesResponse> createUpdateDatesResponse(UpdateDatesResponse value) {
        return new JAXBElement<UpdateDatesResponse>(_UpdateDatesResponse_QNAME, UpdateDatesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link User }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.ws.leonis.md/", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

}
