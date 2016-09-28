package eu.me73.luncheon.user.rest;

import static eu.me73.luncheon.commons.DummyConfig.createBufferedReaderFromFileName;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    UserService userServices;

    @RequestMapping(value = "/users/all", method = RequestMethod.GET, produces = "application/json")
    public Collection<User> getAllUsers() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all users.");
        }
        return userServices.getAllUsers();
    }

    @RequestMapping(value = "users/card/{card}", method = RequestMethod.GET, produces = "application/json")
    public User getUserByCard(@PathVariable String card){
        if (LOG.isDebugEnabled()){
            LOG.debug("Rest request for user with card number: {}", card);
        }
        return userServices.getUserByCard(card);
    }

    @RequestMapping(value = "users/id/{id}", method = RequestMethod.GET, produces = "application/json")
    public User getUserById(@PathVariable Long id){
        if (LOG.isDebugEnabled()){
            LOG.debug("Rest request for user with id number: {}", id);
        }
        return userServices.getUserById(id);
    }

    @RequestMapping(value = "users/pid/{pid}", method = RequestMethod.GET, produces = "application/json")
    public User getUserByPid(@PathVariable String pid){
        if (LOG.isDebugEnabled()){
            LOG.debug("Rest request for user with pid number: {}", pid);
        }
        return userServices.getUserByPid(pid);
    }

    @Async
    @RequestMapping(value = "users/storage/import", method = RequestMethod.GET)
    public String importLocalUsersFromStorage() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for reimporting local users from storage.");
        }
        final Collection<User> users = userServices.getAllUsersFromStorage();
        final int count = users.size();
        userServices.save(users);
        return count + " users imported from storage.";
    }

    @Async
    @RequestMapping(value = "users/file/import", method = RequestMethod.GET)
    public String importLocalUsersFromFile() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for reimporting local users from file.");
        }
        final Collection<User> users;
        int count = 0;
        try {
            users = userServices.importUsersFromFile(createBufferedReaderFromFileName("data/zam.txt"));
            count = users.size();
            userServices.save(users);
        } catch (IOException e) {
            LOG.warn("Error occurred by importing from file ", e);
        }
        return count + " users imported from file.";
    }

    @RequestMapping(value = "users/actual", method = RequestMethod.GET, produces = "application/json")
    public User getUserId() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for actual user.");
        }
        return userServices.getActualUser();
    }
}
