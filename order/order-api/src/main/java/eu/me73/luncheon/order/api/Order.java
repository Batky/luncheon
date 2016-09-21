package eu.me73.luncheon.order.api;

import eu.me73.luncheon.lunch.api.Lunch;
import eu.me73.luncheon.lunch.api.LunchService;
import eu.me73.luncheon.repository.order.OrderEntity;
import eu.me73.luncheon.user.api.User;
import eu.me73.luncheon.user.api.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class Order {

    private Long id = 0L;
    private LocalDate date;
    private User user;
    private Lunch soup;
    private Lunch meal;
    private LocalDateTime changed;

    @Autowired
    UserService userService;

    @Autowired
    LunchService lunchService;

    public Order(final OrderEntity entity) {
        this.id = entity.getId();
        this.date = entity.getDate();
        this.user = userService.getUserById(entity.getUser());
        this.soup = lunchService.getLunchById(entity.getSoup());
        this.meal = lunchService.getLunchById(entity.getMeal());
        this.changed = entity.getChanged();
    }

    public Order() {
    }

    public Order(final Long id,
                 final LocalDate date,
                 final Long user,
                 final Long soup,
                 final Long meal) {
        this.id = id;
        this.date = date;
        this.user = userService.getUserById(user);
        this.soup = lunchService.getLunchById(soup);
        this.meal = lunchService.getLunchById(meal);
        this.changed = LocalDateTime.now();
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
        if (!this.date.equals(date)) {
            this.date = date;
            this.changed = LocalDateTime.now();
        }
    }

    public User getUser() {
        return user;
    }

    public void updateUser(final User user) {
        if (!this.user.equals(user)) {
            this.user = user;
            this.changed = LocalDateTime.now();
        }
    }

    public Lunch getSoup() {
        return soup;
    }

    public void updateSoup(final Lunch soup) {
        if (!this.soup.equals(soup)) {
            this.soup = soup;
            this.changed = LocalDateTime.now();
        }
    }

    public Lunch getMeal() {
        return meal;
    }

    public void updateMeal(final Lunch meal) {
        if (!this.meal.equals(meal)) {
            this.meal = meal;
            this.changed = LocalDateTime.now();
        }
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public OrderEntity toEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(this.id);
        orderEntity.setDate(this.date);
        orderEntity.setSoup(this.soup.getId());
        orderEntity.setMeal(this.meal.getId());
        orderEntity.setUser(this.user.getId());
        orderEntity.setChanged(this.changed);
        return orderEntity;
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

