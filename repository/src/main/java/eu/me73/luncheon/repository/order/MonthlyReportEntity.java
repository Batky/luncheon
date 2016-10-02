package eu.me73.luncheon.repository.order;

import java.io.Serializable;

public class MonthlyReportEntity implements Serializable{

    private String last;
    private String first;
    private Integer count;

    public MonthlyReportEntity() {
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MonthlyReportEntity{" +
                "last='" + last + '\'' +
                ", first='" + first + '\'' +
                ", count=" + count +
                '}';
    }
}
