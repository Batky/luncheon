package eu.me73.luncheon.boot;

import eu.me73.luncheon.user.api.UserService;
import eu.me73.luncheon.user.impl.UserServicesImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LuncheonBeanConfiguration {

    @Bean
    public UserService userService() {
        return new UserServicesImpl();
    }
}
