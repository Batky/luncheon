package eu.me73.luncheon.commons;

import static eu.me73.luncheon.commons.DateUtils.getFirstDate;
import static eu.me73.luncheon.commons.DateUtils.getLastDate;
import static eu.me73.luncheon.commons.DateUtils.getSlovakDayOfWeek;

import ch.qos.logback.classic.Logger;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class DateUtilsTest {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(DateUtilsTest.class);

    @Test
    public void testGetLastDate() throws Exception {
        LOG.info("today is {}", LocalDate.now());
        LOG.info("returning date {}", getLastDate());
        final int dayOfWeek = getLastDate().getDayOfWeek().getValue();
        LOG.info("it is a {} with number representation {}", getSlovakDayOfWeek(getLastDate()), dayOfWeek);
        Assert.assertEquals(dayOfWeek, 5);
    }

    @Test
    public void testGetFirstDate() throws Exception {
        LOG.info("today is {}", LocalDate.now());
        LOG.info("returning date {}", getFirstDate());
        final int dayOfWeek = getFirstDate().getDayOfWeek().getValue();
        LOG.info("it is a {} with number representation {}", getSlovakDayOfWeek(getFirstDate()), dayOfWeek);
        Assert.assertEquals(dayOfWeek, 1);
    }

    @Test
    public void testGetLastDateDefined() throws Exception {
        LocalDate monday = LocalDate.of(2016, 10, 2);
        for (int i=0;i<14;i++) {
            monday = monday.plusDays(1);
            LOG.info("Testing date {} it a day {}", monday, getSlovakDayOfWeek(monday));
            LOG.info("First Pondelok is {} - {}", getFirstDate(monday), getSlovakDayOfWeek(getFirstDate(monday)));
            LOG.info("Last Piatok is {} - {}", getLastDate(monday), getSlovakDayOfWeek(getLastDate(monday)));
            Assert.assertEquals(getFirstDate(monday).getDayOfWeek().getValue(), 1);
            Assert.assertEquals(getLastDate(monday).getDayOfWeek().getValue(), 5);
        }
    }

}