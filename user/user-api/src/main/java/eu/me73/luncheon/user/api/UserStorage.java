package eu.me73.luncheon.user.api;

public interface UserStorage {

    User getUserByCard(final String card);
}
