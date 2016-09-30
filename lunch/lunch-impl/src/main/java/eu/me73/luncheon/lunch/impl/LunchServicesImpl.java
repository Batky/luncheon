package eu.me73.luncheon.lunch.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.commons.LuncheonConfig;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.repository.lunch.LunchDaoService;
import eu.me73.luncheon.repository.lunch.LunchEntity;
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
import org.springframework.stereotype.Component;

@Component
public class LunchServicesImpl implements LunchService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(LunchServicesImpl.class);

    @Autowired
    LunchDaoService service;

    @Autowired
    LuncheonConfig config;

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
                if (date.getYear() >= config.getYear()) {
                    for (String sLunch : dividedLunchDay) {
                        if (counter == 0) {
                            counter++;
                            continue;
                        }
                        counter++;
                        if (sLunch.trim().length() > 255) {
                            LOG.warn("Lunch description is longer as 255 characters");
                            LOG.info("Description: {}", sLunch.trim());
                        } else {
                            Lunch lunch = new Lunch(xid++, (counter <= 3), date, sLunch.trim());
                            if (LOG.isTraceEnabled()) {
                                LOG.trace("Adding lunch {}", lunch);
                            }
                            lunches.add(lunch);
                        }
                    }
                }
            }
        }
        if (LOG.isDebugEnabled()){
            LOG.debug("Returning {} lunches from import file", lunches.size());
        }
        return lunches;
    }

    @Override
    public Lunch getLunchById(final Long id) {
        LunchEntity lunchEntity = service.findOne(id);
        Lunch lunch = null;
        if (Objects.isNull(lunchEntity)) {
            LOG.warn("Cannot find a lunch with id: {}", id);
        } else {
            lunch = new Lunch(lunchEntity);
        }
        return lunch;
    }

    @Override
    public void save(Collection<Lunch> lunches) {
        Objects.requireNonNull(lunches, "Collection cannot be null if collection store is required.");
        lunches.stream().map(Lunch::toEntity).forEach(lunchEntity -> service.save(lunchEntity));
    }

    @Override
    public Lunch getLunchByDayIndex(final LocalDate date, final int index, final boolean soup) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Searching  {} for date {} and index {}", soup ? "soup" : "meal", date, index);
        }
        if (index == 99) {
            return null;
        }

        Collection<LunchEntity> lunches = service.findByDateAndSoupOrderById(date, soup);
        if (Objects.isNull(lunches) || lunches.isEmpty() || lunches.size() == index) {

            return null;
        }

        Lunch lunch = null;

        try {
            lunch = service
                    .findByDateAndSoupOrderById(date, soup)
                    .stream()
                    .map(Lunch::new)
                    .collect(Collectors.toList()).get(index - 1);
        } catch (final Exception e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Cannot find a lunch for date {}, reason {}", date, e.getCause());
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("Exception by searching lunches: ", e);
            }
        }

        return lunch;
    }

    @Override
    public Collection<Lunch> getLunchByDate(final LocalDate date) {
        return service
                .findByDate(date)
                .stream()
                .map(Lunch::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<LunchEntity> findByDateAndSoupOrderById(LocalDate date, boolean soup) {
        return service
                .findByDateAndSoupOrderById(date, soup);
    }
}
