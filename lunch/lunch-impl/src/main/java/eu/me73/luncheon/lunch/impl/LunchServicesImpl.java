package eu.me73.luncheon.lunch.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.repository.lunch.LunchDaoService;
import java.util.Collection;
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

}
