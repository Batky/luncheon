package eu.me73.luncheon.user.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.repository.users.UserDaoService;
import eu.me73.luncheon.repository.users.UserEntity;
import eu.me73.luncheon.repository.users.UserRelation;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.api.UserStorage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServicesImpl implements UserService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(UserServicesImpl.class);

    @Autowired
    private UserStorage userStorage;

    @Autowired
    private UserDaoService service;

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
    public void save(Collection<User> users) {
        Objects.requireNonNull(users, "Saving users collection cannot be null");
        users
                .stream()
                .map(User::toEntity)
                .forEach(userEntity -> service.save(userEntity));
        if (LOG.isDebugEnabled()) {
            LOG.debug("Saved {} users", users.size());
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
    public User getUserByCard(String card) {
        Collection<UserEntity> userEntity = service.findByBarCode(card);
        User user = null;
        if (Objects.nonNull(userEntity) && userEntity.size() > 0) {
            user = userEntity
                    .stream()
                    .map(User::new)
                    .collect(Collectors.toList())
                    .get(0);
        } else {
            if (LOG.isDebugEnabled()) {
                LOG.debug("User with card nr.:{} not found", card);
            }
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsersFromStorage() {
        return this.userStorage.getAllUsersFromStorage();
    }

    @Override
    public User getUserByCardFromStorage(final String card) {
        return this.userStorage.getUserByCard(card);
    }

    @Override
    public User getUserByCredentialsFromStorage(String name, String password) {
        return this.userStorage.getUserByCredentials(name, password);
    }

    @Override
    public Collection<User> importUsersFromFile(final BufferedReader importFile) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Importing users from file {} ", importFile);
        }
        String line;
        Collection<User> users = new ArrayList<>();
        long xid = 1;
        while (Objects.nonNull(line = importFile.readLine())){
            if (LOG.isTraceEnabled()) {
                LOG.trace("Read line {}", line);
            }
            String[] s = line.split(";");
            if (s.length == 4) {
                String nameSplit[] = s[1].split(" ");
                UserRelation type = UserRelation.EMPLOYEE;
                if (s[3].equals("true")) {
                    type = UserRelation.PARTIAL;
                }
                if (nameSplit[1].equals("Návšteva")) {
                    type = UserRelation.VISITOR;
                }
                User user = new User(xid++, s[0], s[2], nameSplit[0], nameSplit[1], type);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("Adding user {}", user);
                }
                users.add(user);
            }
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("Returning {} users from import file", users.size());
        }
        return users;
    }

    @Override
    public User getUserById(Long userId) {
        UserEntity userEntity = service.findOne(userId);
        User user = null;
        if (Objects.isNull(userEntity)) {
            LOG.warn("Cannot find a user with id {}", userId);
        } else {
            user = new User(userEntity);
        }
        return user;
    }

    @Override
    public User getUserByPid(String pid) {
        UserEntity userEntity = service.findByPid(pid);
        User user = null;
        if (Objects.isNull(userEntity)) {
            LOG.warn("Cannot find a user with pid {}", pid);
        } else {
            user = new User(userEntity);
        }
        return user;
    }
}
