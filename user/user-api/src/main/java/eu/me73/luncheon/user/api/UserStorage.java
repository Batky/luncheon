package eu.me73.luncheon.user.api;

import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public interface UserStorage {

    User getUserByCard(final String card);
    Collection<User> getAllUsersFromStorage();
}
