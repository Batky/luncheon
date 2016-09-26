package eu.me73.luncheon.user.impl;

import eu.me73.luncheon.repository.users.UserRelation;
import eu.me73.luncheon.user.api.Role;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserStorage;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class DummyUserStorage implements UserStorage {

    @Override
    public User getUserByCard(final String card) {
        if (card.equals("123")) {
            User user = new User();
            user.setFirstName("Jozef");
            user.setRelation(UserRelation.EMPLOYEE);
            user.setLastName("Bacigal");
            user.setId(666L);
            user.setBarCode("123");
            user.setPid("018");
            user.setLoginName("jozef.bacigal");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(new Role("ROLE_USER"));
            user.setAuthorities(roles);
            return user;
        }
        return null;
    }

    @Override
    public User getUserByCredentials(final String name, final String password) {
        return null;
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
