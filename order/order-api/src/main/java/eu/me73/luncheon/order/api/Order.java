package eu.me73.luncheon.order.api;

import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.repository.order.OrderEntity;
import eu.me73.luncheon.user.api.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private Long id;
    private LocalDate date;
    private User user;
    private Lunch soup;
    private Lunch meal;
    private LocalDateTime changed;
    private String description;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void updateDate(final LocalDate date) {
        this.date = this.updateObject(this.date, date);
    }

    public User getUser() {
        return user;
    }

    public void updateUser(final User user) {
        this.user = this.updateObject(this.user, user);
    }

    public Lunch getSoup() {
        return soup;
    }

    public void updateSoup(final Lunch soup) {
        this.soup = this.updateObject(this.soup, soup);
    }

    public Lunch getMeal() {
        return meal;
    }

    public void updateMeal(final Lunch meal) {
        this.meal = this.updateObject(this.meal, meal);
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = this.updateObject(this.changed, changed);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(this.id);
        orderEntity.setDate(this.date);
        orderEntity.setSoup(this.soup.getId());
        orderEntity.setMeal(this.meal.getId());
        orderEntity.setUser(this.user.getId());
        orderEntity.setChanged(this.changed);
        orderEntity.setDescription(this.description);
        return orderEntity;
    }

    private <T> T updateObject(T thisObject, final T newObject) {
        if (Objects.nonNull(newObject)) {
            if (Objects.isNull(thisObject) || !thisObject.equals(newObject)) {
                thisObject = newObject;
                this.changed = LocalDateTime.now();
            }
        }
        return thisObject;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user +
                ", soup=" + soup +
                ", meal=" + meal +
                ", changed=" + changed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (soup != null ? !soup.equals(order.soup) : order.soup != null) return false;
        return meal != null ? meal.equals(order.meal) : order.meal == null;

    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (soup != null ? soup.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
    }
}

