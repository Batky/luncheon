package eu.me73.luncheon.order.api;

import eu.me73.luncheon.repository.order.OrderEntity;
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

    Collection<Order> getOrdersForUser(Long id, LocalDate fromDate, LocalDate toDate);

    Order updateOrder(final Long id,
                      final LocalDate date,
                      final String pid,
                      final int soup,
                      final int meal);

    Order fromEntity(final OrderEntity entity);

}
