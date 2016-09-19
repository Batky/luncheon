package eu.me73.luncheon.user.impl;

import eu.me73.luncheon.repository.user.UserRelation;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserStorage;

public class DummyUserStorage implements UserStorage {

    @Override
    public User getUserByCard(final String card) {
        User user = new User();
        user.setFirstName("Jozef");
        user.setRelation(UserRelation.EMPLOYEE);
        user.setLastName("Bacigal");
        user.setId(666L);
        user.setBarCode("123");
        return user;
    }
}
