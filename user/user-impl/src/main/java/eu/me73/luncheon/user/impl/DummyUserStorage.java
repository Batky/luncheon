package eu.me73.luncheon.user.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.commons.LuncheonConfig;
import eu.me73.luncheon.repository.users.UserRelation;
import eu.me73.luncheon.user.api.Role;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserStorage;
import java.util.ArrayList;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DummyUserStorage implements UserStorage {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(DummyUserStorage.class);

    @Autowired
    private LuncheonConfig config;

    @Override
    public User getUserByCard(final String card) {
        User user = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Storage search for card {} user", card);
        }
        switch (card) {
//            case "123": {
//                user = new User();
//                user.setFirstName("Jozef");
//                user.setRelation(UserRelation.EMPLOYEE);
//                user.setLastName("Bacigal");
//                user.setId(666L);
//                user.setBarCode("123");
//                user.setPid("018");
//                user.setLoginName("jozef.bacigal");
//                ArrayList<Role> roles = new ArrayList<>();
//                roles.add(new Role("ROLE_USER"));
//                user.setAuthorities(roles);
//                break;
//            }
//            case "power": {
//                user = new User();
//                user.setFirstName("Lubomir");
//                user.setRelation(UserRelation.EMPLOYEE);
//                user.setLastName("Repisky");
//                user.setId(999L);
//                user.setBarCode("123");
//                user.setPid("698");
//                user.setLoginName("lubomir.repisky");
//                ArrayList<Role> roles = new ArrayList<>();
//                roles.add(new Role("ROLE_ADMIN"));
//                roles.add(new Role("ROLE_USER"));
//                user.setAuthorities(roles);
//                break;
//            }
        }
        return user;
    }

    @Override
    public User getUserByCredentials(final String name, final String password) {
        User user = null;
        if (name.equals(config.getPowerName())) {
            if (password.equals(config.getPowerPassword())) {
                user = new User();
                user.setFirstName("Power");
                user.setRelation(UserRelation.POWER_USER);
                user.setLastName("User");
                user.setId(999L);
                user.setBarCode("power");
                user.setPid("pwr");
                user.setLoginName("power.user");
                ArrayList<Role> roles = new ArrayList<>();
                roles.add(new Role("ROLE_ADMIN"));
                roles.add(new Role("ROLE_USER"));
                user.setAuthorities(roles);
            }
        } else {
            if (name.equals(config.getAdminName())) {
                if (password.equals(config.getAdminPassword())) {
                    user = new User();
                    user.setFirstName("Admin");
                    user.setRelation(UserRelation.ADMIN);
                    user.setLastName("User");
                    user.setId(999L);
                    user.setBarCode("admin");
                    user.setPid("adm");
                    user.setLoginName("admin.user");
                    ArrayList<Role> roles = new ArrayList<>();
                    roles.add(new Role("ROLE_SPECIAL"));
                    roles.add(new Role("ROLE_ADMIN"));
                    roles.add(new Role("ROLE_USER"));
                    user.setAuthorities(roles);
                }
            }
        }
        return user;
    }

    @Override
    public Collection<User> getAllUsersFromStorage() {
        Collection<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setFirstName("Jozef");
        user1.setRelation(UserRelation.EMPLOYEE);
        user1.setLastName("Bacigal");
        user1.setId(666L);
        user1.setBarCode("123");
        user1.setPid("018");
        users.add(user1);
        User user2 = new User();
        user2.setFirstName("Lubomir");
        user2.setRelation(UserRelation.POWER_USER);
        user2.setLastName("Repisky");
        user2.setId(999L);
        user2.setBarCode("321");
        user2.setPid("345");
        users.add(user2);
        return users;
    }
}
