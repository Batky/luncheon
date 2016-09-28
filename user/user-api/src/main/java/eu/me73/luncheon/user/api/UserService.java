package eu.me73.luncheon.user.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import org.springframework.stereotype.Service;

/**
 * User service
 */
@Service
public interface UserService {

    void save(final User user);

    /**
     * Store collection of users into database
     * @param users collection non null, non empty
     */
    void save(final Collection<User> users);

    /**
     * All database stored of users
     * @return collection od users
     */
    Collection<User> getAllUsers();
    User getUserByCard(final String card);

    /**
     * Load all users from external storage such as LDAP
     * Import only in memory, not stored in database
     * @return Collection of users
     */
    Collection<User> getAllUsersFromStorage();
    User getUserByCardFromStorage(final String card);
    User getUserByCredentialsFromStorage(final String name, final String password);

    /**
     * Import users from file (old luncheon system used only once)
     * Import is done only in memory, not stored in database
     * @param importFile text file
     * @return Collection of users
     * @throws IOException for the importFile
     */
    Collection<User> importUsersFromFile(final BufferedReader importFile) throws IOException;

    User getUserById(final Long userId);

    User getUserByPid(final String pid);

    void logInUser(final User user);

    void logOutUser();

    User getActualUser();
}
