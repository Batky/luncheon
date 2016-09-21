package eu.me73.luncheon.lunch.rest;

import static eu.me73.luncheon.commons.DateUtils.getFirstDate;
import static eu.me73.luncheon.commons.DateUtils.getLastDate;
import static eu.me73.luncheon.commons.DummyConfig.createBufferedReaderFromFileName;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class LunchRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LunchRestController.class);

    @Autowired
    LunchService lunchService;

    @RequestMapping(value = "lunches/all", method = RequestMethod.GET, produces = "application/json")
    public Collection<Lunch> getAllLunches() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all lunches.");
        }
        return lunchService.getAllLunches();
    }

    @RequestMapping(value = "lunches/lunches", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonSerialize(using = LocalDateSerializer.class)
    public void saveLunches(@RequestBody List<Lunch> lunches) {
        for (Lunch lunch : lunches) {
            lunchService.save(lunch);
        }
    }

    @RequestMapping(value = "lunches/lunch", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @JsonSerialize(using = LocalDateSerializer.class)
    public void saveLunch(@RequestBody Lunch lunch) {
        lunchService.save(lunch);
    }

    @RequestMapping(value = "lunches/date/{date}", method = RequestMethod.GET)
    public Collection<Lunch> getLunchesFromDate(@PathVariable String date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request for lunches for date: {}", date);
        }
        LocalDate dt = null;
        try {
            dt = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (final Exception e) {
            LOG.warn("Error parsing date string {}", date);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Parsing error: ", e);
            }
        }
        return Objects.nonNull(dt) ? lunchService.getAllBetweenDates(getFirstDate(dt), getLastDate(dt)) : null;
    }

    @Async
    @RequestMapping(value = "lunches/file/import", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String importLocalUsersFromFile() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for reimporting local lunches from file.");
        }
        final Collection<Lunch> lunches;
        int count = 0;
        try {
            lunches = lunchService.importLunchesFromFile(createBufferedReaderFromFileName("data/obed.txt"));
            count = lunches.size();
            lunchService.save(lunches);
        } catch (IOException e) {
            LOG.warn("Error occurred by importing from file ", e);
        }
        return count + " lunches imported from file.";
    }

}
