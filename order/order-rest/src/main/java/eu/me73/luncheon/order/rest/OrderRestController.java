package eu.me73.luncheon.order.rest;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import java.util.Collection;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class OrderRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderRestController.class);

    @Autowired
    OrderService userServices;

    @RequestMapping(value = "/orders/all", method = RequestMethod.GET, produces = "application/json")
    public Collection<Order> getAllOrders() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all orders.");
        }
        return userServices.getAllOrders();
    }

    @RequestMapping(value = "/orders/orders", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunches(@RequestBody List<Order> orders) {
        for (Order order : orders) {
            userServices.save(order);
        }
    }

    @RequestMapping(value = "/orders/order", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLunch(@RequestBody Order order) {
        userServices.save(order);
    }
}
