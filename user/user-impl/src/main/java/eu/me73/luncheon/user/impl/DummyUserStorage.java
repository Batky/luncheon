package eu.me73.luncheon.user.impl;

import eu.me73.luncheon.repository.user.UserRelation;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserStorage;
import java.util.ArrayList;
import java.util.Collection;

public class DummyUserStorage implements UserStorage {

    @Override
    public User getUserByCard(final String card) {
        User user = new User();
        user.setFirstName("Jozef");
        user.setRelation(UserRelation.EMPLOYEE);
        user.setLastName("Bacigal");
        user.setId(666L);
        user.setBarCode("123");
        user.setPid("018");
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
