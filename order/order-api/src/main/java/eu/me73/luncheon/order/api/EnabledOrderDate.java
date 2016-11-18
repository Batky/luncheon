package eu.me73.luncheon.order.api;

import ch.qos.logback.classic.Logger;
import eu.me73.luncheon.repository.order.EnabledEntity;
import java.time.LocalDate;
import java.util.Objects;
import org.slf4j.LoggerFactory;

public class EnabledOrderDate {

    private final Logger LOG = (Logger) LoggerFactory.getLogger(EnabledOrderDate.class);

    private Integer month;
    private Integer year;
    private LocalDate date;

    public EnabledOrderDate(final Integer month, final Integer year) {
        this.month = month;
        this.year = year;
        this.date = LocalDate.of(year, month, 1);
    }

    public EnabledOrderDate() {
    }

    public EnabledOrderDate(final EnabledEntity enabledEntity) {
        if (Objects.isNull(enabledEntity)) {
            LOG.warn("Creating new enabled order date is not possible, probably never set in DB.");
            this.year = 1999;
            this.month = 1;
        } else {
            this.year = enabledEntity.getYear();
            this.month = enabledEntity.getMonth();
            this.date = LocalDate.of(this.year, this.month, 1);
        }
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
        this.date = LocalDate.of(this.year, this.month, 1);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
        this.date = LocalDate.of(this.year, this.month, 1);
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "EnabledOrderDate{" +
                "month=" + month +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnabledOrderDate that = (EnabledOrderDate) o;

        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        return year != null ? year.equals(that.year) : that.year == null;

    }

    @Override
    public int hashCode() {
        int result = month != null ? month.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        return result;
    }

    public EnabledEntity toEntity() {
        EnabledEntity enabledEntity = new EnabledEntity();
        enabledEntity.setId(1L);
        enabledEntity.setMonth(this.month);
        enabledEntity.setYear(this.year);
        return enabledEntity;
    }
}
