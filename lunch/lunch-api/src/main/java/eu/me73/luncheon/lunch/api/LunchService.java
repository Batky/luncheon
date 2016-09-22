package eu.me73.luncheon.lunch.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public interface LunchService {

    void save(final Lunch lunch);
    void save(final Collection<Lunch> lunches);
    Collection<Lunch> getAllLunches();
    Collection<Lunch> getAllBetweenDates(final LocalDate fromDate, final LocalDate toDate);
    Collection<Lunch> importLunchesFromFile(final BufferedReader importFile) throws IOException;

    Lunch getLunchById(final Long id);

    /**
     * Returns lunch by index in day counting separately for soups and meals from 1
     * @param date date of lunch
     * @param index index of meal that day
     * @param soup if true mean search in soups else search in main meal
     * @return found lunch
     */
    Lunch getLunchByDayIndex(final LocalDate date, final int index, final boolean soup);
}
