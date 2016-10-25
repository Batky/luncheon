package eu.me73.luncheon.commons;

import ch.qos.logback.classic.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DateUtils {

    @Autowired
    LuncheonConfig config;

    private static final Logger LOG = (Logger) LoggerFactory.getLogger(DateUtils.class);

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return last date of friday of two work weeks, taking actual date
     */
    public LocalDate getLastDate(){
        return getLastDate(LocalDate.now());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return first date of monday of two work weeks, taking actual date
     */
    public LocalDate getFirstDate(){
        return getFirstDate(LocalDate.now());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return last date of friday of two work weeks
     */
    public LocalDate getLastDate(final LocalDate date){
        return date.plusDays(12 - date.getDayOfWeek().getValue());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return first date of monday of two work weeks
     */
    public LocalDate getFirstDate(final LocalDate date){
        return date.minusDays(date.getDayOfWeek().getValue() - 1 );
    }

    /**
     * Using language tag "sk-SK"
     * @param date
     * @return slovak day of week
     */
    public String getSlovakDayOfWeek(final LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("sk-SK"));
    }

    public LocalDate getLocalDate(final String date) {
        LocalDate dt = null;
        try {
            dt = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (final Exception e) {
            LOG.warn("Error parsing date string {}", date);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Parsing error: ", e);
            }
        }
        return dt;
    }

    public boolean itsChangeable(final LocalDate date) {

        final LocalDate actualDate = LocalDate.now();
        final LocalDate tomorrowDate = actualDate.plusDays(1);
        final LocalTime actualTime = LocalTime.now();
        final LocalTime configOrderingHour = LocalTime.of(config.getOrdering(), 0);
        final LocalTime configSameDayOrderingHourStart = LocalTime.of(config.getSameDayStartHour(), config.getSameDayStartMinutes());
        final LocalTime configSameDayOrderingHourEnd = LocalTime.of(config.getSameDayEndHour(), config.getSameDayEndMinutes());

        if (date.isEqual(actualDate)) {
            if (actualTime.isAfter(configSameDayOrderingHourStart) && actualTime.isBefore(configSameDayOrderingHourEnd)) {
                return true;
            }
        }

        if (date.isBefore(tomorrowDate)) {
            return false;
        }

        if (date.equals(tomorrowDate)) {
            return !actualTime.isAfter(configOrderingHour);
        }

        return true;

    }

}
