package eu.me73.luncheon.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"eu.me73.luncheon.repository.*"})
@EnableJpaRepositories(basePackages = {"eu.me73.luncheon.repository.*"})
@ComponentScan(basePackages = {"eu.me73.luncheon.*"})
@Import(LuncheonBeanConfiguration.class)
public class LuncheonApplication {

    public static void main(String[] args) {
        SpringApplication.run(LuncheonApplication.class, args);
    }
}
