package eu.me73.luncheon.lunch.api;

import java.util.Collection;

public interface LunchService {

    void save(final Lunch order);
    Collection<Lunch> getAllLunches();

}
