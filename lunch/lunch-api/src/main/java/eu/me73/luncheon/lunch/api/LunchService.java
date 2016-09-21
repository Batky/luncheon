package eu.me73.luncheon.lunch.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;

public interface LunchService {

    void save(final Lunch order);
    Collection<Lunch> getAllLunches();
    Collection<Lunch> getAllBetweenDates(final LocalDate fromDate, final LocalDate toDate);
    Collection<Lunch> importLunchesFromFile(final BufferedReader importFile) throws IOException;

    Lunch getLunchById(final Long lunchId);
}
