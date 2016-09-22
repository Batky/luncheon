package eu.me73.luncheon.order.rest;

import static eu.me73.luncheon.commons.DateUtils.getFirstDate;
import static eu.me73.luncheon.commons.DateUtils.getLastDate;
import static eu.me73.luncheon.commons.DateUtils.getLocalDate;
import static eu.me73.luncheon.commons.DummyConfig.createBufferedReaderFromFileName;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import eu.me73.luncheon.order.api.UserOrder;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
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
public class OrderRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderRestController.class);

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "orders/all", method = RequestMethod.GET, produces = "application/json")
    public Collection<Order> getAllOrders() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all orders.");
        }
        return orderService.getAllOrders();
    }

    @RequestMapping(value = "orders/orders", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunches(@RequestBody List<Order> orders) {
        for (Order order : orders) {
            orderService.save(order);
        }
    }

    @RequestMapping(value = "orders/order", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunch(@RequestBody Order order) {
        orderService.save(order);
    }

    @RequestMapping(value = "orders/date/{date}/id/{id}", method = RequestMethod.GET)
    public Collection<UserOrder> getOrdersForUserFromDate(@PathVariable String date, @PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request for orders for user {} and date: {}", id, date);
        }
        LocalDate dt = getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.getOrdersForUser(id, getFirstDate(dt), getLastDate(dt)) : null;
    }

    @RequestMapping(value = "orders/store/user", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUsersLunches(@RequestBody List<UserOrder> userOrders) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request to store user orders {}", userOrders.toArray());
        }
        orderService.storeOrdersForUser(userOrders);
    }

    @Async
    @RequestMapping(value = "orders/file/import", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String importLocalUsersFromFile() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for reimporting local orders from file.");
        }
        final Collection<Order> orders;
        int count = 0;
        try {
            orders = orderService.importOrdersFromFile(createBufferedReaderFromFileName("data/objednavky.txt"))
                    .stream()
                    .filter(order ->
                            Objects.nonNull(order.getUser()) &&
                            Objects.nonNull(order.getSoup()) &&
                            Objects.nonNull(order.getMeal()))
                    .collect(Collectors.toList());
            count = orders.size();
            if (LOG.isDebugEnabled()) {
                LOG.debug("Orders count after null user and null meal removal {}", count);
            }
            orderService.save(orders);
        } catch (IOException e) {
            LOG.warn("Error occurred by importing from file ", e);
        }
        return count + " orders imported from file.";
    }

}
