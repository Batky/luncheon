package eu.me73.luncheon.commons;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "luncheon")
public class LuncheonConfig {

    private Integer ordering;
    private Integer year;
    private Double employee;
    private Double visitor;
    private Double partial;

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

    public Double getEmployee() {
        return employee;
    }

    public void setEmployee(Double employee) {
        this.employee = employee;
    }

    public Double getVisitor() {
        return visitor;
    }

    public void setVisitor(Double visitor) {
        this.visitor = visitor;
    }

    public Double getPartial() {
        return partial;
    }

    public void setPartial(Double partial) {
        this.partial = partial;
    }
}
