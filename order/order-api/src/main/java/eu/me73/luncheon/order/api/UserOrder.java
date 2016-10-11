package eu.me73.luncheon.order.api;

import eu.me73.luncheon.lunch.api.Lunch;

public class UserOrder {

    private Long user;
    private Lunch lunch;
    private boolean ordered;
    private boolean changeable;
    private String description;

    public UserOrder() {
    }

    public UserOrder(
            final Long user,
            final boolean ordered,
            final Lunch lunch,
            final boolean changeable,
            final String description) {
        this.user = user;
        this.ordered = ordered;
        this.lunch = lunch;
        this.changeable = changeable;
        this.description = description;
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

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "user=" + user +
                ", lunch=" + lunch +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
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
