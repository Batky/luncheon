package eu.me73.luncheon.order.api;

public class UserStatistics {

    private String name;
    private int lunchesThisMonth;
    private boolean today;
    private boolean tomorrow;

    public UserStatistics(final String name,
                          final int lunchesThisMonth,
                          final boolean today,
                          final boolean tomorrow) {
        this.name = name;
        this.lunchesThisMonth = lunchesThisMonth;
        this.today = today;
        this.tomorrow = tomorrow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLunchesThisMonth() {
        return lunchesThisMonth;
    }

    public void setLunchesThisMonth(int lunchesThisMonth) {
        this.lunchesThisMonth = lunchesThisMonth;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }

    public boolean isTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(boolean tomorrow) {
        this.tomorrow = tomorrow;
    }

    @Override
    public String toString() {
        return "UserStatistics{" +
                "name='" + name + '\'' +
                ", lunchesThisMonth=" + lunchesThisMonth +
                ", today=" + today +
                ", tomorrow=" + tomorrow +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserStatistics that = (UserStatistics) o;

        if (lunchesThisMonth != that.lunchesThisMonth) return false;
        if (today != that.today) return false;
        if (tomorrow != that.tomorrow) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + lunchesThisMonth;
        result = 31 * result + (today ? 1 : 0);
        result = 31 * result + (tomorrow ? 1 : 0);
        return result;
    }
}
