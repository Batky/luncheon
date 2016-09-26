package eu.me73.luncheon.commons;

import ch.qos.logback.classic.Logger;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilsTest {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(DateUtilsTest.class);

    private DateUtils dateUtils;

    @Before
    public void startUp() {
        this.dateUtils = new DateUtils();
    }

    @Test
    public void testGetLastDate() throws Exception {
        LOG.info("today is {}", LocalDate.now());
        LOG.info("returning date {}", dateUtils.getLastDate());
        final int dayOfWeek = dateUtils.getLastDate().getDayOfWeek().getValue();
        LOG.info("it is a {} with number representation {}", dateUtils.getSlovakDayOfWeek(dateUtils.getLastDate()), dayOfWeek);
        Assert.assertEquals(dayOfWeek, 5);
    }

    @Test
    public void testGetFirstDate() throws Exception {
        LOG.info("today is {}", LocalDate.now());
        LOG.info("returning date {}", dateUtils.getFirstDate());
        final int dayOfWeek = dateUtils.getFirstDate().getDayOfWeek().getValue();
        LOG.info("it is a {} with number representation {}", dateUtils.getSlovakDayOfWeek(dateUtils.getFirstDate()), dayOfWeek);
        Assert.assertEquals(dayOfWeek, 1);
    }

    @Test
    public void testGetLastDateDefined() throws Exception {
        LocalDate monday = LocalDate.of(2016, 10, 2);
        for (int i=0;i<14;i++) {
            monday = monday.plusDays(1);
            LOG.info("Testing date {} it a day {}", monday, dateUtils.getSlovakDayOfWeek(monday));
            LOG.info("First Pondelok is {} - {}", dateUtils.getFirstDate(monday), dateUtils.getSlovakDayOfWeek(dateUtils.getFirstDate(monday)));
            LOG.info("Last Piatok is {} - {}", dateUtils.getLastDate(monday), dateUtils.getSlovakDayOfWeek(dateUtils.getLastDate(monday)));
            Assert.assertEquals(dateUtils.getFirstDate(monday).getDayOfWeek().getValue(), 1);
            Assert.assertEquals(dateUtils.getLastDate(monday).getDayOfWeek().getValue(), 5);
        }
    }

    @Test
    public void testItsChangeable() throws Exception {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate today = LocalDate.now();

        Assert.assertTrue(dateUtils.itsChangeable(tomorrow));
        Assert.assertFalse(dateUtils.itsChangeable(yesterday));

        LOG.info("Actual time is {}", LocalTime.now());
        LOG.info("Result from 'itsChangeable' for today is {}", dateUtils.itsChangeable(today));

    }

}