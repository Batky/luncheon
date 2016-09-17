package eu.me73.luncheon.order.api;

import java.util.Collection;

public interface OrderService {

    void save(final Order order);

    Collection<Order> getAllOrders();

}
