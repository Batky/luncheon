package eu.me73.luncheon.order.impl;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import eu.me73.luncheon.repository.order.OrderDaoService;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServicesImpl implements OrderService {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderServicesImpl.class);

    @Autowired
    OrderDaoService service;

    @Override
    public void save(final Order order) {
        if (Objects.nonNull(order)) {
            service.save(order.toEntity());
            if (LOG.isDebugEnabled()) {
                LOG.debug("Order {} saved.", order);
            }
        }
    }

    @Override
    public Collection<Order> getAllOrders() {
        return service
                .findAll()
                .stream()
                .map(Order::new)
                .collect(Collectors.toList());
    }

}