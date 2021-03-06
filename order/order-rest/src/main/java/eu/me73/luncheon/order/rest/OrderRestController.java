package eu.me73.luncheon.order.rest;

import static eu.me73.luncheon.commons.DummyConfig.createBufferedReaderFromFileName;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.commons.DateUtils;
import eu.me73.luncheon.order.api.*;
import eu.me73.luncheon.user.api.UserService;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.FileCopyUtils;
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

    @Autowired
    OrderEnabledService orderEnabledService;

    @Autowired
    UserService userService;

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
            order.updateChangedBy(userService.getActualUser());
            orderService.save(order);
        }
    }

    @RequestMapping(value = "orders/order", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunch(@RequestBody Order order) {
        order.updateChangedBy(userService.getActualUser());
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

    @RequestMapping(value = "orders/daily/summary/morning/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<DailyReportSummary> getDailyReportSummaryMorning(@PathVariable String date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for daily orders report summary mornings adds, date: {}", date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ? orderService.createDailySummaryMorning(dt) : null;
    }

    @RequestMapping(value = "orders/monthly/summary/{date}", method = RequestMethod.GET, produces = "application/json")
    public Collection<MonthlyReport> getMonthlyReport(@PathVariable String date) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for monthly orders report summary for date {}", date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        return Objects.nonNull(dt) ?  orderService.createMonthlyReport(dt) : null;
    }

    @RequestMapping(value = "orders/monthly/olymp/{date}", method = RequestMethod.GET)
    public void downloadOlympFile(HttpServletResponse response, @PathVariable String date) throws IOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for olymp file for date {}", date);
        }
        LocalDate dt = dateUtils.getLocalDate(date);
        String fileName = Objects.nonNull(dt) ? orderService.createOlympFile(dt) : null;
        Objects.requireNonNull(fileName, "Cannot find export olymp file");
        File file = new File(fileName);

        if(!file.exists()){
            LOG.warn("Sorry. The export olymp file does not exist");
        } else {
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @RequestMapping(value = "orders/lock", method = RequestMethod.GET, produces = "application/json")
    public EnabledOrderDate getLockDate(HttpServletResponse response) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for lock date.");
        }
        EnabledOrderDate date = orderEnabledService.getDate();
        if (Objects.isNull(date)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
        return date;
    }

    @RequestMapping(value = "orders/lock/{date}", method = RequestMethod.POST, consumes = "application/json")
    public void setLockDate(HttpServletResponse response, @PathVariable String date) {
        LocalDate dt = dateUtils.getLocalDate(date);
        EnabledOrderDate enabledOrderDate = orderEnabledService.getDate();
        if (Objects.nonNull((enabledOrderDate)) && Objects.nonNull(dt)) {
            if (enabledOrderDate.getDate().isBefore(dt)) {
                orderEnabledService.save(new EnabledOrderDate(dt.getMonth().getValue(), dt.getYear()));
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "orders/stats/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserStatistics getStatistics(@PathVariable Long id) {
        return orderService.gainStatistics(id, LocalDate.now());
    }
}
