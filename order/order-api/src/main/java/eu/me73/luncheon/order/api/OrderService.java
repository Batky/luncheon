package eu.me73.luncheon.order.api;

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

    Collection<UserOrder> getOrdersForUser(Long id, LocalDate fromDate, LocalDate toDate);

    String storeOrdersForUser(Collection<UserOrder> userOrders);

    void delete(final Collection<Order> orders);

    Order updateOrder(final Long id,
                      final LocalDate date,
                      final String pid,
                      final int soup,
                      final int meal);

    Order getOrderForId(final Long id);
}
