package eu.me73.luncheon.boot;

import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.lunch.impl.LunchServicesImpl;
import eu.me73.luncheon.order.api.OrderService;
import eu.me73.luncheon.order.impl.OrderServicesImpl;
import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.api.UserStorage;
import eu.me73.luncheon.user.impl.DummyUserStorage;
import eu.me73.luncheon.user.impl.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuncheonBeanConfiguration {

    @Bean
    public UserService userService() {
        return new UserServicesImpl();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServicesImpl();
    }

    @Bean
    public LunchService lunchService() {
        return new LunchServicesImpl();
    }

    @Bean
    public UserStorage userStorage() {
        return new DummyUserStorage();
    }
}
