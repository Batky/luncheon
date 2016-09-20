package eu.me73.luncheon.user.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

public interface UserService {

    void save(final User user);
    Collection<User> getAllUsers();
    void userSource(final UserStorage userStorage);
    User getUserByCard(final String card);
    User getUserByCardFromStorage(final String card);
    Collection<User> importUsersFromFile(final BufferedReader importFile) throws IOException;

}
