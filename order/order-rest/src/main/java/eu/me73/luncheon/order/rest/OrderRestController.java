package eu.me73.luncheon.order.rest;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.order.api.Order;
import eu.me73.luncheon.order.api.OrderService;
import java.util.Collection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class OrderRestController {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(OrderRestController.class);

    @Autowired
    OrderService userServices;

    @RequestMapping(value = "/orders", method = RequestMethod.GET, produces = "application/json")
    public Collection<Order> getAllOrders(){
        if (LOG.isDebugEnabled()) {
            LOG.debug("Rest request for all orders.");
        }
        return userServices.getAllOrders();
    }
}
