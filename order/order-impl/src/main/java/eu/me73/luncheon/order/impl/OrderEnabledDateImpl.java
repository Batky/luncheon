package eu.me73.luncheon.order.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.commons.LuncheonConfig;
import eu.me73.luncheon.order.api.EnabledOrderDate;
import eu.me73.luncheon.order.api.OrderEnabledService;
import eu.me73.luncheon.repository.order.OrderEnabledDaoService;
import java.text.Collator;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEnabledDateImpl implements OrderEnabledService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderEnabledDateImpl.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.ENGLISH);
    private static final Collator SVK_COLLATOR = Collator.getInstance(Locale.forLanguageTag("sk"));

    @Autowired
    OrderEnabledDaoService service;

    @Autowired
    LuncheonConfig config;

    private  Map<Long, String> numberingMap = new HashMap<>();
    private final String [] soupStrings = {"1","2"};
    private final String [] mealStrings = {"1","2","3","4","5"};

    @Override
    public void save(final EnabledOrderDate orderDate) {
        if (Objects.nonNull(orderDate)) {
            service.save(orderDate.toEntity());
            if (LOG.isDebugEnabled()) {
                LOG.debug("First enabled change date {} saved.", orderDate);
            }
        }
    }

    @Override
    public EnabledOrderDate getDate() {
        return new EnabledOrderDate(service.getOne(1L));
    }
}