package eu.me73.luncheon.order.api;

import eu.me73.luncheon.repository.order.EnabledEntity;
import java.time.LocalDate;

public class EnabledOrderDate {

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
        this.year = enabledEntity.getYear();
        this.month = enabledEntity.getMonth();
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
