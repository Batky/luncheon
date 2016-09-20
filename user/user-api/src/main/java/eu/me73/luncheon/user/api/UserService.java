package eu.me73.luncheon.user.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

public interface UserService {

    void save(final User user);
    void save(final Collection<User> users);

    Collection<User> getAllUsers();
    User getUserByCard(final String card);

    Collection<User> getAllUsersFromStorage();
    User getUserByCardFromStorage(final String card);

    Collection<User> importUsersFromFile(final BufferedReader importFile) throws IOException;

}
