package eu.me73.luncheon.commons;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtils {

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return last date of friday of two work weeks, taking actual date
     */
    public static LocalDate getLastDate(){
        return getLastDate(LocalDate.now());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return first date of monday of two work weeks, taking actual date
     */
    public static LocalDate getFirstDate(){
        return getFirstDate(LocalDate.now());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return last date of friday of two work weeks
     */
    public static LocalDate getLastDate(final LocalDate date){
        return date.plusDays(12 - date.getDayOfWeek().getValue());
    }

    /**
     * Luncheon in standard working in two work weeks. Two weeks period is counted from actual date
     * @return first date of monday of two work weeks
     */
    public static LocalDate getFirstDate(final LocalDate date){
        return date.minusDays(date.getDayOfWeek().getValue() - 1 );
    }

    /**
     * Using language tag "sk-SK"
     * @param date
     * @return slovak day of week
     */
    public static String getSlovakDayOfWeek(final LocalDate date) {
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("sk-SK"));
    }
}
