package eu.me73.luncheon.order.api;

import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.repository.order.OrderEntity;
import eu.me73.luncheon.user.api.User;

public class Order {

    private Long id = 0L;
    private User user;
    private Lunch lunch;

    public Order(final OrderEntity entity) {
        this.id = entity.getId();
        this.user = new User(entity.getUser());
        this.lunch = new Lunch(entity.getLunch());
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lunch getLunch() {
        return lunch;
    }

    public void setLunch(Lunch lunch) {
        this.lunch = lunch;
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(this.getId());
        orderEntity.setLunch(this.lunch.toEntity());
        orderEntity.setUser(this.user.toEntity());
        return orderEntity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", lunch=" + lunch +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        return lunch != null ? lunch.equals(order.lunch) : order.lunch == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (lunch != null ? lunch.hashCode() : 0);
        return result;
    }
}

