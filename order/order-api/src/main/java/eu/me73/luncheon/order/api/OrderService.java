package eu.me73.luncheon.order.api;

import eu.me73.luncheon.user.api.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    void save(final Order order);
    void save(final Collection<Order> orders);

    Collection<Order> getAllOrders();
    Collection<Order> importOrdersFromFile(final BufferedReader importFile) throws IOException;

    Collection<UserOrder> getOrdersForUser(final Long id, final LocalDate fromDate, final LocalDate toDate);

    String storeOrdersForUser(final Collection<UserOrder> userOrders, final User user);

    void delete(final Collection<Order> orders);

    Order updateOrder(final Long id,
                      final LocalDate date,
                      final String pid,
                      final int soup,
                      final int meal);

    Order getOrderForId(final Long id);

    Collection<DailyReport> createReport(final LocalDate date);

    Collection<DailyReportSummary> createDailySummary(final LocalDate date);

    Collection<DailyReportSummary> createDailySummaryMorning(final LocalDate date);

    Collection<MonthlyReport> createMonthlyReport(final LocalDate date);

    String createOlympFile(final LocalDate date);
}
