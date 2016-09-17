package eu.me73.luncheon.order.api;

import eu.me73.luncheon.repository.order.OrderEntity;

public class Order {

    private Long id = 0L;
    private Long userId;
    private Long lunchId;

    public Order(final OrderEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.lunchId = entity.getLunchId();
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getUser() {
        return userId;
    }

    public void setUser(Long user) {
        this.userId = user;
    }

    public Long getLunch() {
        return lunchId;
    }

    public void setLunch(Long lunch) {
        this.lunchId = lunch;
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(this.getId());
        orderEntity.setLunchId(this.lunchId);
        orderEntity.setUserId(this.userId);
        return orderEntity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + userId +
                ", lunch=" + lunchId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!id.equals(order.id)) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        return lunchId != null ? lunchId.equals(order.lunchId) : order.lunchId == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (lunchId != null ? lunchId.hashCode() : 0);
        return result;
    }
}

