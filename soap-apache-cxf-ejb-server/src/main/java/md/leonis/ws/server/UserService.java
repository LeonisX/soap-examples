package md.leonis.ws.server;

import md.leonis.ws.server.model.Dates;
import md.leonis.ws.server.model.User;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface UserService {

    User create();

    // @WebParam need on interface level
    User get(@WebParam(name = "id") Integer id);

    User updateDates(@WebParam(name = "id") Integer id, @WebParam(name = "dates") Dates dates);

    User addHobbie(@WebParam(name = "id") Integer id, @WebParam(name = "hobbie") String hobbie);

    User addNote(@WebParam(name = "id") Integer id, @WebParam(name = "key") String key, @WebParam(name = "value") int value);

    User save(@WebParam(name = "user") User user);
}
