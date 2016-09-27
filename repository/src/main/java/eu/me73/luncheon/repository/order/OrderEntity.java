package eu.me73.luncheon.repository.order;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_order")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 3162207396887246017L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Column(name = "date")
    LocalDate date;

    @Column(name = "soup")
    Long soup;

    @Column(name = "meal")
    Long meal;

    @Column(name = "user_id")
    Long user;

    @Column(name = "changed")
    LocalDateTime changed;

    public OrderEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoup() {
        return soup;
    }

    public void setSoup(Long soup) {
        this.soup = soup;
    }

    public Long getMeal() {
        return meal;
    }

    public void setMeal(Long meal) {
        this.meal = meal;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", date=" + date +
                ", soup=" + soup +
                ", meal=" + meal +
                ", user=" + user +
                ", changed=" + changed +
                '}';
    }
}
