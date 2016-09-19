package eu.me73.luncheon.user.rest;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.impl.DummyUserStorage;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(UserRestController.class);

    private boolean initialized = false;

    @Autowired
    UserService userServices;

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    public Collection<User> getAllUsers() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all users.");
        }
        return userServices.getAllUsers();
    }

    @RequestMapping(value = "users/card/{card}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable String card){
        if (!initialized) {
            userServices.userSource(new DummyUserStorage());
            initialized = true;
        }
        return userServices.getUserByCardFromStorage(card);
    }

    /*
    @RequestMapping(value = "users/login/{login}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable String login){
        return userServices.getUserByLogin(login);
    }

    @RequestMapping(value = "users/user", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user){
        userServices.save(user);
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id){
        userServices.deleteUser(id);
    }
*/
}
