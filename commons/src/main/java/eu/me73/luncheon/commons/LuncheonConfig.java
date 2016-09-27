package eu.me73.luncheon.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "luncheon")
public class LuncheonConfig {

    private Integer ordering;
    private Integer year;

    public Integer getOrdering() {
        return ordering;
    }

    public void setOrdering(Integer ordering) {
        this.ordering = ordering;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
