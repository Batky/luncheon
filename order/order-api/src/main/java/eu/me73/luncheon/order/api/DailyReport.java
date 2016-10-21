package eu.me73.luncheon.order.api;

import java.time.LocalDateTime;

public class DailyReport {

    private String name;
    private String soup;
    private String meal;
    private LocalDateTime changed;

    public DailyReport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "DailyReport{" +
                "name='" + name + '\'' +
                ", soup='" + soup + '\'' +
                ", meal='" + meal + '\'' +
                ", changed=" + changed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyReport that = (DailyReport) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (soup != null ? !soup.equals(that.soup) : that.soup != null) return false;
        return meal != null ? meal.equals(that.meal) : that.meal == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (soup != null ? soup.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
    }

}
