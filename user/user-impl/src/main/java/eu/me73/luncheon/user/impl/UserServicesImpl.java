package eu.me73.luncheon.user.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.repository.user.UserDaoService;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.api.UserStorage;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServicesImpl implements UserService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(UserServicesImpl.class);

    private UserStorage userStorage;

    @Autowired
    UserDaoService service;

    @Override
    public void save(final User user) {
        if (Objects.nonNull(user)) {
            service.save(user.toEntity());
            if (LOG.isDebugEnabled()) {
                LOG.debug("User {} saved.", user);
            }
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return service
                .findAll()
                .stream()
                .map(User::new)
                .collect(Collectors.toList());
    }

    @Override
    public void userSource(final UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User getUserByCard(String card) {
        return service
                .findByBarCode(card)
                .stream()
                .map(User::new)
                .collect(Collectors.toList())
                .get(0);
    }

    @Override
    public User getUserByCardFromStorage(final String card) {
        return Objects.nonNull(this.userStorage) ? this.userStorage.getUserByCard(card) : null;
    }

}
