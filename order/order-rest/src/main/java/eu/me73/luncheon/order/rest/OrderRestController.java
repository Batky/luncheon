package eu.me73.luncheon.order.rest;

import static eu.me73.luncheon.commons.DummyConfig.createBufferedReaderFromFileName;

import ch.qos.logback.classic.Logger;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import eu.me73.luncheon.commons.DateUtils;
import eu.me73.luncheon.order.api.DailyReport;
import eu.me73.luncheon.order.api.DailyReportSummary;
import eu.me73.luncheon.order.api.MonthlyReport;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import eu.me73.luncheon.order.api.UserOrder;
import eu.me73.luncheon.repository.order.OrderEntity;
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

    @Autowired
    DateUtils dateUtils;

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

    @RequestMapping(value = "orders/date/{date}/user/{id}", method = RequestMethod.GET)
    public Collection<UserOrder> getOrdersForUserFromDate(@PathVariable String date, @PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request for orders for user {} and date: {}", id, date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.getOrdersForUser(id, dateUtils.getFirstDate(dt), dateUtils.getLastDate(dt)) : null;
    }

    @RequestMapping(value = "orders/id/{id}", method = RequestMethod.GET)
    public Order getOrdersById(@PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request for orders with id {} ", id);
        }
        return orderService.getOrderForId(id);
    }

    @RequestMapping(value = "orders/exact/date/{date}/user/{id}", method = RequestMethod.GET)
    public Collection<UserOrder> getOrdersForUserForExactDate(@PathVariable String date, @PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Request for orders for user {} and exact date: {}", id, date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.getOrdersForUser(id, dt, dt) : null;
    }

    @RequestMapping(value = "orders/store/user", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveUsersLunches(@RequestBody List<UserOrder> userOrders) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request to store {} user orders", userOrders.size());
        }
        return orderService.storeOrdersForUser(userOrders);
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

    @RequestMapping(value = "orders/daily/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<DailyReport> getDailyReport(@PathVariable String date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for daily orders report, date: {}", date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.createReport(dt) : null;
    }

    @RequestMapping(value = "orders/daily/summary/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<DailyReportSummary> getDailyReportSummary(@PathVariable String date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for daily orders report summary, date: {}", date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.createDailySummary(dt) : null;
    }

    @RequestMapping(value = "orders/monthly/summary", method = RequestMethod.GET, produces = "application/json")
    public Collection<MonthlyReport> getMonthlyReport() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for monthly orders report summary");
        }
        return orderService.createMonthlyReport(LocalDate.of(2016, 1, 1));
    }

}
