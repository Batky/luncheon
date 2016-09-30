package eu.me73.luncheon.order.api;

public class DailyReportSummary {

    private long helpId;
    private String mark;
    private String meal;
    private int count;

    public DailyReportSummary() {
    }

    public DailyReportSummary(final long id, final String mark, final String meal) {
        this.helpId = id;
        this.mark = mark;
        this.meal = meal;
        this.count = 0;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void incCount() {
        this.count++;
    }

    public long getId() {
        return helpId;
    }

    public void setId(long helpId) {
        this.helpId = helpId;
    }

    @Override
    public String toString() {
        return "DailyReportSummary{" +
                "helpId=" + helpId +
                ", mark='" + mark + '\'' +
                ", meal='" + meal + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyReportSummary summary = (DailyReportSummary) o;

        if (count != summary.count) return false;
        if (mark != null ? !mark.equals(summary.mark) : summary.mark != null) return false;
        return meal != null ? meal.equals(summary.meal) : summary.meal == null;

    }

    @Override
    public int hashCode() {
        int result = mark != null ? mark.hashCode() : 0;
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        result = 31 * result + count;
        return result;
    }
}
