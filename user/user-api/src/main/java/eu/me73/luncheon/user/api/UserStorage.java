package eu.me73.luncheon.user.api;

import java.util.Collection;

public interface UserStorage {

    User getUserByCard(final String card);
    Collection<User> getAllUsersFromStorage();
}
