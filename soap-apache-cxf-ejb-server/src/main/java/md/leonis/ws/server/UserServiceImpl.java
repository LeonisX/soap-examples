package md.leonis.ws.server;

import md.leonis.ws.server.model.Dates;
import md.leonis.ws.server.model.User;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;

@WebService(endpointInterface = "md.leonis.ws.server.UserService", serviceName = "userService")
public class UserServiceImpl implements UserService {

    // Simple database
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public User create() {
        User user = new User(users.entrySet().size() + 1, "newUser");
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(Integer id) {
        // Error checks are not implemented
        return users.get(id);
    }

    @Override
    public User updateDates(Integer id, Dates dates) {
        User user = users.get(id);
        user.setDates(dates);
        return user;
    }

    @Override
    public User addHobbie(Integer id, String hobbie) {
        User user = users.get(id);
        user.getHobbies().add(hobbie);
        return user;
    }

    @Override
    public User addNote(Integer id, String key, int value) {
        User user = users.get(id);
        user.getNotes().put(key, value);
        return user;
    }

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
