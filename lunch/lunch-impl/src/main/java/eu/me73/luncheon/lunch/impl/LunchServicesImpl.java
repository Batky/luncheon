package eu.me73.luncheon.lunch.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.repository.lunch.LunchDaoService;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LunchServicesImpl implements LunchService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LunchServicesImpl.class);

    @Autowired
    LunchDaoService service;

    @Override
    public void save(final Lunch lunch) {
        if (Objects.nonNull(lunch)) {
            service.save(lunch.toEntity());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Lunch {} saved.", lunch);
            }
        }
    }

    @Override
    public Collection<Lunch> getAllLunches() {
        return service
                .findAll()
                .stream()
                .map(Lunch::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Lunch> getAllBetweenDates(final LocalDate fromDate, final LocalDate toDate) {
        return service
                .findByDateGreaterThanEqualAndDateLessThanEqualOrderByDate(fromDate, toDate)
                .stream()
                .map(Lunch::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Lunch> importLunchesFromFile(final BufferedReader importFile) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Importing lunches from file {} ", importFile);
        }
        String line;
        Collection<Lunch> lunches = new ArrayList<>();
        long xid = 1;
        while(Objects.nonNull(line = importFile.readLine())){
            if (LOG.isTraceEnabled()) {
                LOG.trace("Read line {}", line);
            }
            String[] dividedLunchDay = line.split(";");
            if (dividedLunchDay.length == 8) {
                int counter = 0;
                LocalDate date = LocalDate.parse(dividedLunchDay[0], DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH));
                for (String sLunch : dividedLunchDay) {
                    if (counter == 0) {
                        counter++;
                        continue;
                    }
                    counter++;
                    Lunch lunch = new Lunch(xid++, (counter < 3), date, sLunch);
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("Adding lunch {}", lunch);
                    }
                    lunches.add(lunch);
                }
            }
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("Returning {} lunches from import file", lunches.size());
        }
        return lunches;
    }

    @Override
    public Lunch getLunchById(Long lunchId) {
        return new Lunch(service.findOne(lunchId));
    }
}
