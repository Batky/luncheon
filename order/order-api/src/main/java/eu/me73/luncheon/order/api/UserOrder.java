package eu.me73.luncheon.order.api;

import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.user.api.User;
import java.time.LocalDate;

public class UserOrder {

    private Long user;
    private boolean ordered;
    private Lunch lunch;

    public UserOrder() {
    }

    public UserOrder(Long user, boolean ordered, Lunch lunch) {
        this.user = user;
        this.ordered = ordered;
        this.lunch = lunch;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public Lunch getLunch() {
        return lunch;
    }

    public void setLunch(Lunch lunch) {
        this.lunch = lunch;
    }

    public LocalDate getDate() {
        return lunch.getDate();
    }

    @Override
    public String toString() {
        return "UserOrders{" +
                "user=" + user +
                ", ordered=" + ordered +
                ", lunch=" + lunch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserOrder that = (UserOrder) o;

        if (ordered != that.ordered) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        return lunch != null ? lunch.equals(that.lunch) : that.lunch == null;

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (ordered ? 1 : 0);
        result = 31 * result + (lunch != null ? lunch.hashCode() : 0);
        return result;
    }
}
