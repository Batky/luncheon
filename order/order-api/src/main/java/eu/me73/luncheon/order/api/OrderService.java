package eu.me73.luncheon.order.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

public interface OrderService {

    void save(final Order order);
    void save(final Collection<Order> orders);

    Collection<Order> getAllOrders();

    Collection<Order> importOrdersFromFile(final BufferedReader importFile) throws IOException;

}
