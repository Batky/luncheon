package eu.me73.luncheon.boot;

import eu.me73.luncheon.commons.DateUtils;
import eu.me73.luncheon.commons.LuncheonConfig;
import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.impl.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LuncheonBeanConfiguration {

    @Bean
    public LuncheonConfig config() {
        return new LuncheonConfig();
    }

    @Bean
    public DateUtils dateUtils() {
        return new DateUtils();
    }

    @Bean
    @Primary
    public UserService userService() {
        return new UserServicesImpl();
    }

//    @Bean
//    public OrderService orderService() {
//        return new OrderServicesImpl();
//    }

//    @Bean
//    public LunchService lunchService() {
//        return new LunchServicesImpl();
//    }

//    @Bean
//    public UserStorage userStorage() {
//        return new DummyUserStorage();
//    }
}