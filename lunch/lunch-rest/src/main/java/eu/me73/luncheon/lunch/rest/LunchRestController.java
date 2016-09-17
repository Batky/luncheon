package eu.me73.luncheon.lunch.rest;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import java.util.Collection;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/lunches", method = RequestMethod.GET, produces = "application/json")
    public Collection<Lunch> getAllLunches() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all lunches.");
        }
        return lunchService.getAllLunches();
    }

    @RequestMapping(value = "/lunches/lunches", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunches(@RequestBody List<Lunch> lunches) {
        for (Lunch lunch : lunches) {
            lunchService.save(lunch);
        }
    }

    @RequestMapping(value = "/lunches/lunch", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunch(@RequestBody Lunch lunch) {
        lunchService.save(lunch);
    }

}